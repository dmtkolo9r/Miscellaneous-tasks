package humanresources;

import java.util.*;

public class Departament implements EmployeeGroup {

    private static final int DEFAULT_CAPACITY = 8;
    private String name;
    private Employee[] employees;
    private int size;

    public Departament(){
        this("");
    }
    public Departament(String name) {
        this(name, DEFAULT_CAPACITY);
    }

    public Departament(String name, int capacity) {
        this.name = name;
        if (capacity <= 0)
            throw new NegativeSizeException("Negative capacity");
        employees = new Employee[capacity];
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

//    public int employeesQuantity(JobTitilesEnum jobTitle){
//        int employeeQuantity = 0;
//        for (int i = 0; i < size; i++) {
//            assert false;
//            if (employees[i].getJobTitle().equals(jobTitle))
//                employeeQuantity++;
//        }
//        return employeeQuantity;
//    }
//

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

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

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

//    @Override
//    public int countPartTimeEmployee() {
//        int countPartTimeEmployee = 0;
//        for (int i = 0; i < size; i++) {
//            if (employees[i].isPartTimer())
//                countPartTimeEmployee++;
//        }
//        return countPartTimeEmployee;
//    }
//
//    @Override
//    public int countFullTimeEmployee() {
//        int countFullTimeEmployee = 0;
//        for (int i = 0; i < size; i++) {
//            if (!employees[i].isPartTimer())
//                countFullTimeEmployee++;
//        }
//        return countFullTimeEmployee;
//    }
//
//    @Override
//    public int countEmployeeTraveller() {
//        int countEmployeeTraveller = 0;
//        for (int i = 0; i < size; i++) {
//            if (employees[i].isTraveller())
//                countEmployeeTraveller++;
//        }
//        return countEmployeeTraveller;
//    }

    public static Employee[] sortBySalaryAndBonus(Employee[] arrEmp){
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

    private static Employee[] sortMerge(Employee[] arrEmployees) {
        if (arrEmployees.length < 2) return arrEmployees;
        int middle = arrEmployees.length / 2;
        return merge(sortMerge(Arrays.copyOfRange(arrEmployees, 0, middle)),
                sortMerge(Arrays.copyOfRange(arrEmployees, middle, arrEmployees.length)));
    }

    private static Employee[] merge(Employee[] arrEmployeesOne, Employee[] arrEmployeesTwo) {
        int lenArrOne = arrEmployeesOne.length, lenArrTwo = arrEmployeesTwo.length;
        int countArrOne = 0, countArrTwo = 0, lenAll = lenArrOne + lenArrTwo;
        Employee[] result = new Employee[lenAll];
        for (int i = 0; i < lenAll; i++) {
            if (countArrTwo < lenArrTwo && countArrOne < lenArrOne) {
                if (arrEmployeesOne[countArrOne].getSalary() > arrEmployeesTwo[countArrTwo].getSalary()) {
                    countArrTwo++;
                    result[i] = arrEmployeesTwo[countArrTwo];
                } else {
                    countArrOne++;
                    result[i] = arrEmployeesOne[countArrOne];
                }
            } else if (countArrTwo < lenArrTwo) {
                countArrTwo++;
                result[i] = arrEmployeesTwo[countArrTwo];
            } else {
                countArrOne++;
                result[i] = arrEmployeesOne[countArrOne];
            }
        }
        return result;
    }

    public boolean remove(Employee employee){

        for (int i = 0; i < employeeQuantity(); i++) {
            if(employees[i].equals(employee)){
                shift(i);
                return true;
            }
        }
        return false;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

    @Override
    public Employee get(int index) {
        return null;
    }

    @Override
    public Employee set(int index, Employee element) {
        return null;
    }

    @Override
    public void add(int index, Employee element) {

    }

    @Override
    public Employee remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        return null;
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return new Iterator<Employee>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Employee next() {
                if(hasNext()) {
                    return employees[currentIndex++];
                }
                throw new NoSuchElementException("No element");
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Employee employee) {
        return false;
    }
}
