package io;

import humanresources.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class ControlledDepartamentManager extends DepartamentsManager {
    protected Source<EmployeeGroup> source;

    public Source<EmployeeGroup> getSource() {
        return source;
    }

    public void setSource(Source<EmployeeGroup> source) {
        this.source = source;
    }

    public ControlledDepartamentManager(String name, EmployeeGroup[] employeeGroups) {
        super(name, employeeGroups);
    }

    public ControlledDepartamentManager(String groupsName) {
        super(groupsName);
    }

    private void createControlledEmployeeGroup(EmployeeGroup employeeGroup) {
        ControlledDepartament departament = new ControlledDepartament(employeeGroup);
        source.create(departament);
    }

    private void delete(EmployeeGroup employeeGroup) {
        source.delete(employeeGroup);
    }

    @Override
    public boolean add(EmployeeGroup employeeGroup) {
        if(super.add(employeeGroup)){
            createControlledEmployeeGroup(employeeGroup);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o){
        if (super.remove(o)) {
            delete((EmployeeGroup) o);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends EmployeeGroup> c){
        boolean isChanged = false;
        c.forEach(this::createControlledEmployeeGroup);
        for (EmployeeGroup group : c) {
            if (add(new ControlledDepartament(group)))
                isChanged = true;
        }
        return isChanged;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c){
        boolean isChanged = false;
        for(EmployeeGroup group : c){
            add(index, new ControlledDepartament(group));
            isChanged = true;
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        boolean areRemoved = false;
        for(Object o : c){
            if (remove(o))
                areRemoved = true;
        }
        return areRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c){
        boolean areRetained = false;
        for(Object o : c){
            if(!c.contains(o)) {
                if (remove(o))
                areRetained = true;
            }
        }
        return areRetained;
    }

    @Override
    public void clear(){
        super.clear();
        this.forEach(this::delete);
    }

    @Override
    public void add(int index, EmployeeGroup element){
        super.add(index, element);
        createControlledEmployeeGroup(element);
    }

    @Override
    public EmployeeGroup remove(int index){
        EmployeeGroup removedOrder = super.remove(index);
        if (Objects.nonNull(removedOrder))
            delete(super.get(index));
        return removedOrder;
    }

    public void store() {
        this.stream().filter(order -> ((ControlledDepartament) order).isChanged).forEach(order -> {
            source.store(order);
        });
    }

    public void load() {
        this.forEach(order -> {
            source.load(order);
        });
    }
}
