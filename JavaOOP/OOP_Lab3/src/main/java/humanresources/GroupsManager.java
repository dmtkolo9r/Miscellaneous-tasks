package humanresources;

public interface GroupsManager {

    public int employeesQuantity();
    public int groupsQuantity();
    public void add(EmployeeGroup group) throws AlreadyAddedException;
    public EmployeeGroup getEmployeeGroup(String name);
    public EmployeeGroup[] getEmployeesGroups();
    public int employeesQuantity(JobTitilesEnum jobTitle);
    public EmployeeGroup getEmployeeGroup(String firstName, String secondName);
    public Employee mostValuableEmployee();
    public boolean remove(String groupName);
    public int remove(EmployeeGroup group);
    public int countPartTimeEmployee();
    public int countFullTimeEmployee();
    public int countEmployeeTraveller();
    public Employee[] getEmployeeTraveller();

}
