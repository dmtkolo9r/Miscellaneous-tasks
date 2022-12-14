package humanresources;

import java.io.Serializable;
import java.util.*;

public class Departament extends AbstractListArray<Employee> implements EmployeeGroup, List<Employee>, Comparator<Employee>, Serializable {

    private static final int DEFAULT_CAPACITY = 8;
    private String name;
    private Employee[] employees;
    private int size;

    public Departament(){
        this("");
    }

    public Departament(int capacity){
        if (capacity <= 0)
            throw new NegativeSizeException("Negative capacity");
        employees = new Employee[capacity];
    }

    public Departament(String name) {
        this(name, DEFAULT_CAPACITY);
    }

    public Departament(String name, int capacity) {
        this(capacity);
        this.name = name;
    }

    public Departament(String name, Employee[] employees) {
        this.name = name;
        if(employees.length > 0) {
            Employee[] newEmployees = new Employee[employees.length];
            System.arraycopy(employees, 0, newEmployees, 0, employees.length);
            this.employees = newEmployees;
        } else {
            this.employees = new Employee[DEFAULT_CAPACITY];
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasEmployee(String firstName, String lastName){
        return indexOf(firstName, lastName) != -1;
    }

    private int indexOf(String firstName, String lastName){
        for (int i = 0; i < size; i++) {
            Employee employee = employees[i];
            if(employee.getFirstName().equals(firstName) && employee.getSecondName().equals(lastName)) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(Employee employee) {
        return super.add(employee);
    }

    public boolean remove(Object o){
        return super.remove(o);
    }

    public boolean addAll(Collection<? extends Employee> c){
        return super.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Employee> c){
        return super.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c){
        return super.removeAll(c);
    }

    public boolean retainAll(Collection<?> c){
        return super.retainAll(c);
    }

    public Employee set(int index, Employee element){
        return super.set(index, element);
    }

    public void add(int index, Employee element){
        super.add(index, element);
    }

    public Employee remove(int index){
        return super.remove(index);
    }

    public boolean remove(String firstName, String secondName) {
        if(indexOf(firstName, secondName) != -1){
            shift(indexOf(firstName, secondName));
            this.employees[size] = null;
            this.size--;
            return true;
        }
        return false;
    }

    public int employeeQuantity() {

        return size;
    }

    private void shift(int i) {

        System.arraycopy(this.employees, i + 1, this.employees, i, size - i);
    }

    public Employee[] getEmployees() {

        Employee[] newEmployee = new Employee[this.size];
        System.arraycopy(this.employees, 0, newEmployee, 0, this.size);
        return newEmployee;
    }

    public Employee[] getEmployees(String jobTitle) {

        Employee[] newEmployee = new Employee[this.size];
        int count = 0;
        for (int i = 0; i < this.size; i++) {
            if (employees[i].getJobTitle().equals(jobTitle)) {
                newEmployee[count] = employees[i];
                count++;
            }
        }
        Employee[] newJobTitleEmployees = new Employee[count];
        System.arraycopy(newEmployee, 0, newJobTitleEmployees, 0, count);
        return newJobTitleEmployees;
    }

    public Employee[] employeesSortedBySalary(){

        return sortMerge(this.employees);
    }

    public Employee[] sortBySalaryAndBonus(Employee[] arrEmp){
        Arrays.sort(arrEmp);
        return arrEmp;
    }

    @Override
    public Employee[] getEmployeeTraveller() {
        Employee[] newEmployees = new Employee[size];
        int countEmployee = 0;
        for (int i = 0; i < size; i++) {
            if(employees[i].isTraveller())
                newEmployees[countEmployee++] = employees[i];
        }
        Employee[] getEmployeeTraveller = new Employee[countEmployee];
        System.arraycopy(getEmployeeTraveller, 0, newEmployees, 0, countEmployee);
        return getEmployeeTraveller;
    }

    private Employee[] sortMerge(Employee[] arrEmployees) {
        Arrays.sort(arrEmployees, this::compare);
        return arrEmployees;
    }

    public int removeAll(JobTitilesEnum jobTitle){

        int countRemovedJobTitleEmployee = 0;

        for (int i = 0; i < employeeQuantity(); i++) {
            if(employees[i].getJobTitle().equals(jobTitle)){
                shift(i);
                this.employees[size] = null;
                this.size--;
                countRemovedJobTitleEmployee++;
            }
        }
        return countRemovedJobTitleEmployee;
    }

    public Employee getEmployee(String firstName, String lastName){
        if(indexOf(firstName, lastName) != -1)
            return employees[indexOf(firstName, lastName)];
        else
            return null;
    }

    public JobTitilesEnum[] jobTitles(){
        JobTitilesEnum[] jobTitles = JobTitilesEnum.values();
        for (int i = 0; i < jobTitles.length; i++) {
            jobTitles[i] = null;
        }
        for (int i = 0; i < employeeQuantity(); i++) {
            JobTitilesEnum jobTitle = employees[i].getJobTitle();
            jobTitles[jobTitle.ordinal()] =jobTitle;
        }
        JobTitilesEnum[] newJobTitles = new JobTitilesEnum[jobTitles.length];
        int countJobTitle = 0;
        for (int i = 0; i < jobTitles.length; i++) {
            if(jobTitles[i] != null){
                newJobTitles[countJobTitle] = jobTitles[i];
                countJobTitle++;
            }
        }

        System.arraycopy(newJobTitles, 0 , newJobTitles, 0, countJobTitle);

        return newJobTitles;
    }

    public Employee bestEmployee(){
        Employee employeeWithBestSalary = employees[0];
        for (int i = 1; i < employeeQuantity(); i++) {
            if(employees[i].getSalary() > employeeWithBestSalary.getSalary()){
                employeeWithBestSalary = employees[i];
            }
        }
        return employeeWithBestSalary;
    }

    public Employee[] businessTravellers(){

        Employee[] newEmployee = new Employee[employeeQuantity()];
        int count = 0;
        for (int i = 0; i < employeeQuantity(); i++) {
            if(employees[i].isTraveller()){
                newEmployee[count] = employees[i];
                count++;

            }
        }
        Employee[] businessTravellers = new Employee[count];
        System.arraycopy(newEmployee, 0, businessTravellers, 0, count);
        return businessTravellers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Departament");
        sb.append(" ").
                append(name).
                append(":").
                append(size).
                append("\n");
        if (size >= 0) {
            for (int i = 0; i < size; i++) {
                sb.append('<').append(employees[i]).append(">\n");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getSalary() - o2.getSalary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o instanceof Class) return false;
        Departament that = (Departament) o;
        return size == that.size &&
                getName().equals(that.getName()) &&
                Arrays.deepEquals(getEmployees(), that.getEmployees());
    }

    @Override
    public int hashCode() {
        int objectHashCode = Objects.hashCode(employees[0]);
        int positionHashCode = Objects.hashCode(0);
        for (int i = 1; i < size; i++) {
            objectHashCode ^= Objects.hashCode(employees[i]);
            positionHashCode ^= i;
        }
        return objectHashCode ^ positionHashCode;
    }
}
