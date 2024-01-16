import java.util.*;
import java.io.*;
import java.sql.*;

class myException extends RuntimeException {
    myException(String msg) {
        super(msg);

    }
}

public class EMS {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3310/ems", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println((con != null) ? "Success" : "Failed");
            System.out.println(
                    "                           WELCOME TO EMPLOYEE MANGEMANET SYSTEM                             ");

            int choice = 0;
            do {
                System.out.println("ENTER YOUR CHOICE : ");
                System.out.println("1 ADMIN LOGIN\n2 EXIT ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("**********ADMIN LOGIN**********");
                        Admin e = new Admin();
                        e.AdminLogin();

                        int choice1;

                        do {
                            System.out.println("ENTER YOUR CHOICE : ");
                            System.out.println("1.ADD EMPLOYEE");
                            System.out.println("2.DELETE EMPLOYEE");
                            System.out.println("3.VIEW ALL EMPLOYEE");
                            System.out.println("4.UPDATE EMPLOYEE");
                            System.out.println("5.SEARCH EMPLOYEE");
                            System.out.println("6.CREATE EMPLOYEE DETAILFILE");
                            System.out.println("7.UPDATE SALARY");
                            System.out.println("8.APPROVE LEAVE");
                            System.out.println("9.EXIT");

                            choice1 = sc.nextInt();
                            switch (choice1) {
                                case 1:
                                    e.addEmployee(con);
                                    System.out.println("*****************************");
                                    break;
                                case 2:
                                    e.deleteEmployee(con);
                                    System.out.println("*****************************");
                                    break;
                                case 3:
                                    System.out.println("*****************************");
                                    e.viewAllEmployee(con);
                                    System.out.println("*****************************");
                                    break;
                                case 4:
                                    System.out.println("*****************************");
                                    e.updateEmployee(con);
                                    System.out.println("*****************************");
                                    break;
                                case 5:
                                    System.out.println("*****************************");
                                    e.searchEmployee(con);
                                    System.out.println("*****************************");
                                    break;
                                case 6:
                                    System.out.println("*****************************");
                                    System.out.print("YOU WANT TO CREATE FILE OF EMPLOYEE(YES/NO) : ");
                                    String ans = sc.next();
                                    if (ans.equalsIgnoreCase("YES")) {
                                        System.out.print("ENTER ID YOU WANT TO GENERATE FILE : ");
                                        int id = sc.nextInt();
                                        e.createEmpdetailsFile(con, ans, id);
                                    } else {
                                        System.out.println("NOT GENERATED");
                                    }
                                    System.out.println("*****************************");
                                    break;
                                case 7:
                                    System.out.println("*****************************");
                                    e.updateSalary(con);
                                    System.out.println("*****************************");
                                    break;
                                case 8:
                                    System.out.print("YOU WANT TO APPROVE LEAVE OF EMPLOYEE : ");
                                    String answer = sc.next();
                                    e.approveLeave(answer, con);
                                    System.out.println("*****************************");
                                    break;
                                case 9:
                                    System.out.println("*****************************");
                                    System.out.println("EXIT FROM ADMIN");
                                    System.exit(0);
                                    System.out.println("*****************************");
                                    break;

                                default:
                                    System.out.println("INVALID CHOICE!!");
                                    break;
                            }
                        } while (choice1 != 9);
                    case 2:
                        System.out.println("EXIT FROM APPLICATION");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("INVALID CHOICE!");
                        break;

                }

            } while (choice != 3);
        } catch (myException e) {
            System.out.println("SOME PROBLEM DETECTED!");
        }
    }
}

class Admin {
    Scanner sc = new Scanner(System.in);

    boolean start = false;
    String default_adminName = "stuti";
    String default_adminPassword = "2011";
    int i = 0;

    void AdminLogin() throws Exception { // user login method

        for (int i = 1; i <= 4; i++) {
            System.out.println("ENTER ADMIN NAME FOR LOGIN : ");
            String user_id = sc.next();
            if (user_id.equals(default_adminName)) {
                start = true;
                userPassword();// password method
                break;
            } else {
                System.out.println("INVALID ADMIN NAME");
                start = false;
            }
        }
        if (start == false) {
            System.out.println("ACCOUNT IS LOCKED FOR 1 HOUR TRY LATER!!!");
            System.exit(0);
        }
    }

    void userPassword() throws Exception {// method for admin password
        System.out.println("NOW ENTER USER PASSWORD HERE : ");
        String pass = sc.next();
        boolean flag = false;
        do {
            if (pass.equalsIgnoreCase(default_adminPassword)) {
                System.out.println("ADMIN SUCCSESSFULLY LOGIN ");
                System.out.println("*****************************");
                flag = true;
                break;
            } else {
                flag = true;
                System.out.println("PASSWORD IS INCORRECT");
                System.out.println("RE-SET PASSWORD!!!");
                resetUserPassword();
            }
        } while (flag == false);
    }

    void resetUserPassword() throws Exception {// user reset your password here
        String pass;
        boolean flag = false;
        do {
            System.out.println("ENTER NEW PASSWORD OF USER : ");
            pass = sc.next();
            int Save = pass.length();
            if (Save == 4) {
                for (int j = 0; j < Save; j++) {
                    if (pass.charAt(j) >= '0' && pass.charAt(j) <= '9') {
                        default_adminPassword = pass;
                        AdminLogin();
                        flag = true;
                        break;
                    } else {
                        System.out.println("INVALID COMBINATION...PLEASE RE-ENTER PASSWORD");
                        flag = false;
                        break;
                    }
                }
            } else {
                System.out.println("INVALID COMBINATION...PLEASE RE-ENTER PASSWORD");
            }
        } while (flag == false);
    }

    void addEmployee(Connection con) throws Exception {
        try {
            con.setAutoCommit(false);
            System.out.print("ENTER EMPLOYEE FIRSTNAME : ");
            String e_firstname = sc.next();
            System.out.print("ENTER EMPLOYEE LASTNAME : ");
            String e_lastname = sc.next();
            System.out.print("ENTER EMPLOYEE DESIGNATION : ");
            String e_designation = sc.next();
            System.out.print("ENTER EMPLOYEE SALARY : ");
            double e_salary = sc.nextDouble();
            System.out.print("ENTER EMPLOYEE AGE : ");
            int e_age = sc.nextInt();
            System.out.print("ENTER EMPLOYEE GENDER : ");
            String e_gender = sc.next();
            String e_email = null;
            System.out.print("DO YOU WANT TO ADD EMAIL : ");
            String s2 = sc.next();
            if (s2.equalsIgnoreCase("yes")) {
                while (true) {
                    System.out.print("ENTER EMAIL : ");
                    e_email = sc.next();
                    if (e_email.endsWith("@gmail.com") || (e_email.endsWith("@yahoo.com"))
                            || (e_email.endsWith("@outlook.com"))) {
                        break;
                    } else {
                        System.out.println("INVALID MAIL FORMAT!!!");
                    }
                }
            }
            String e_PhoneNo;
            boolean flag = false;
            do {
                System.out.print("ENTER CONTACT NUMER : ");
                e_PhoneNo = sc.next();
                int Save = e_PhoneNo.length();
                if (Save == 10) {
                    for (int j = 0; j < Save; j++) {
                        if (e_PhoneNo.charAt(j) >= '0' && e_PhoneNo.charAt(j) <= '9') {
                            flag = true;
                        } else {
                            flag = false;
                            System.out.println("invalid Mobile Number Pls Try Again:");
                            break;
                        }
                    }
                } else {
                    System.out.println("invalid Mobile Number Pls Try Again:");
                }
            } while (flag == false);

            System.out.print("ENTER EMPLOYEE HIREDATE : ");
            String e_hireDate = sc.next();
            String sql1 = "select * from department";
            Statement st1 = con.createStatement();
            ResultSet rs = st1.executeQuery(sql1);
            int row_count = 0;
            while (rs.next()) {
                System.out.print("DEPARTMENT ID: " + rs.getInt(1));
                System.out.print("  DEPARTMENT NAME: " + rs.getString(2));
                System.out.println();
                row_count++;
            }
            int department_id;
            System.out.print("DO YOU HAVE TO INSERT WHAT IS IN TABLE : ");
            String ans = sc.next();
            if (ans.equalsIgnoreCase("yes")) {
                do {
                    System.out.print("ASSIGN A DEPARTMENT ID TO EMPLOYEE FOR ABOVE LIST : ");
                    department_id = sc.nextInt();
                    if (department_id > 0 || department_id < row_count) {
                    } else {
                        System.out.println("ENTER VALID DEPARTMENT ID.");
                    }
                } while (department_id < 0 || department_id > row_count);
            } else {
                System.out.print("ASSIGN NEW DEPARTMENT ID TO THE DEPARTMENT: ");
                department_id = sc.nextInt();
                System.out.print("ENTER DEPARTMENT NAME OF EMPLOYEE : ");
                String dname = sc.next();
                String sql9 = "insert into department values(?,?)";
                PreparedStatement pst9 = con.prepareStatement(sql9);
                pst9.setInt(1, department_id);
                pst9.setString(2, dname);
                pst9.executeUpdate();
            }
            System.out.print("ENTER PATH FOR PHOTO : ");
            String path = sc.next();
            FileInputStream fis = new FileInputStream(path);

            System.out.println("INSERTION STARTED......");
            String sql = "INSERT INTO `employee`(`e_fname`, `e_lname`, `e_desg`,`e_salary`, `e_age`, `gender`, `email`, `phone_no`, `e_hiredate`, `e_photo`, `d_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, e_firstname);
            pst.setString(2, e_lastname);
            pst.setString(3, e_designation);
            pst.setDouble(4, e_salary);
            pst.setInt(5, e_age);
            pst.setString(6, e_gender);
            pst.setString(7, e_email);
            pst.setString(8, e_PhoneNo);
            pst.setString(9, e_hireDate);
            pst.setBinaryStream(10, fis);
            pst.setInt(11, department_id);
            if (pst.executeUpdate() > 0) {
                System.out.println("SUCCESSFULLY INSERTED IN EMPLOYEE TABLE");
                String sql5 = "Select e_id from employee where e_fname=?";
                PreparedStatement pst1 = con.prepareStatement(sql5);
                pst1.setString(1, e_firstname);
                ResultSet rs1 = pst1.executeQuery();
                int eid = 0;
                while (rs1.next()) {
                    eid = rs1.getInt("e_id");
                }
                String sql2 = "insert into salary(e_id,salaryAmount,PayrollDate) values(?,?,?)";
                System.out.print("ENTER PAYROLL DATE : ");
                String date = sc.next();
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setInt(1, eid);
                pst2.setDouble(2, e_salary);
                pst2.setString(3, date);
                System.out.println(pst2.executeUpdate());
                String sql3 = "insert into leave_status(e_id) values(?)";
                PreparedStatement pst3 = con.prepareStatement(sql3);
                pst3.setInt(1, eid);
                pst3.executeUpdate();
                con.commit();
            } else {
                System.out.println("INSERTION FAILED");
            }
        } catch (myException e) {
            System.out.println("THERE IS SOME ERROR IN YOU ADD EMPLOYEE METHOD!");
        }
    }

    void deleteEmployee(Connection con) throws Exception {
        try {
            con.setAutoCommit(false);
            System.out.print("ENTER ID OF EMPLOYEE YOU WANT TO DELETE : ");
            int id = sc.nextInt();
            String sql2 = "delete from leave_status where e_id =?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setInt(1, id);
            pst2.executeUpdate();
            String sql3 = "DELETE FROM salary WHERE e_id=?";
            PreparedStatement pst3 = con.prepareStatement(sql3);
            pst3.setInt(1, id);
            int rs = pst3.executeUpdate();
            if (rs > 0) {
                String sql1 = "delete from employee where e_id =?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                pst1.setInt(1, id);
                System.out.println((pst1.executeUpdate() > 0) ? "SUCCESSFULLY DELETED" : "DELETION DENIED");

            }
            con.commit();
        } catch (myException e) {
            System.out.println("THERE IS SOME ERROR IN YOUR DELETION METHOD.");
        }
    }

    void updateEmployee(Connection con) throws Exception {
        con.setAutoCommit(false);
        System.out.print("ENTER ID YOU WANT TO UPDATE : ");
        int id = sc.nextInt();
        System.out.print("ENTER NEW DESIGNATION : ");
        String ndes = sc.next();
        String eemail = null;
        System.out.print("DO YOU WANT TO ADD EMAIL OF EMPLOYEE : ");
        String s3 = sc.next();
        if (s3.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.print("ENTER NEW EMAIL OF EMPLOYEE : ");
                eemail = sc.next();
                if (eemail.endsWith("@gmail.com") || (eemail.endsWith("@yahoo.com"))
                        || (eemail.endsWith("@outlook.com"))) {
                    break;
                } else {
                    System.out.println("INVALID MAIL FORMAT!!!");
                }
            }
        }
        String ePhoneNo;
        boolean flag1 = false;
        do {
            System.out.print("ENTER NEW CONTACT NUMER OF : ");
            ePhoneNo = sc.next();
            int Save1 = ePhoneNo.length();
            if (Save1 == 10) {
                for (int j = 0; j < Save1; j++) {
                    if (ePhoneNo.charAt(j) >= '0' && ePhoneNo.charAt(j) <= '9') {
                        flag1 = true;
                    } else {
                        flag1 = false;
                        System.out.println("INVALID MOBILE NUMBER PLEASE TRY AGAIN : ");
                        break;
                    }
                }
            } else {
                System.out.println("INVALID MOBILE NUMBER PLEASE TRY AGAIN : ");
            }
        } while (flag1 == false);
        String sql = "update employee set e_desg=?,email=?,phone_no=? where e_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, ndes);
        pst.setString(2, eemail);
        pst.setString(3, ePhoneNo);
        pst.setInt(4, id);
        System.out.println((pst.executeUpdate() > 0) ? "SUCCESSFULLY UPDATED" : "UPDATION FAILED");
        con.commit();
    }

    void searchEmployee(Connection con) throws Exception {
        con.setAutoCommit(false);
        System.out.print("ENTER ID FOR SEARCH : ");
        int id = sc.nextInt();
        String sql = "select * from employee where e_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("EMPLOYEE FIRST NAME : " + rs.getString(2));
            System.out.println("EMPLOYEE LAST NAME : " + rs.getString(3));
            System.out.println("EMPLOYEE DESIGANTION : " + rs.getString(4));
            System.out.println("EMPLOYEE SALARY: " + rs.getDouble(5));
            System.out.println("EMPLOYEE AGE : " + rs.getString(6));
            System.out.println("EMPLOYEE GENDER : " + rs.getString(7));
            System.out.println("EMPLOYEE EMAIL : " + rs.getString(8));
            System.out.println("EMPLOYEE PHONE NO. : " + rs.getString(9));
            System.out.println("EMPLOYEE HIREDATE  : " + rs.getString(10));
            String sql2 = "SELECT d_name FROM Department WHERE d_id=?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setInt(1, rs.getInt(12));
            ResultSet rs2 = pst2.executeQuery();
            String dname = null;
            while (rs2.next()) {
                dname = rs2.getString(1);
            }
            System.out.println("EMPLOYEE DEPARTMENT NAME : " + dname);
        }
        con.commit();
    }

    void viewAllEmployee(Connection con) throws Exception {
        con.setAutoCommit(false);
        String sql = "select * from employee";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("EMPLOYEE ID : " + rs.getString(1));
            System.out.println("EMPLOYEE FIRST NAME : " + rs.getString(2));
            System.out.println("EMPLOYEE LAST NAME : " + rs.getString(3));
            System.out.println("EMPLOYEE DESIGANTION : " + rs.getString(4));
            System.out.println("EMPLOYEE SALARY: " + rs.getDouble(5));
            System.out.println("EMPLOYEE AGE : " + rs.getString(6));
            System.out.println("EMPLOYEE GENDER : " + rs.getString(7));
            System.out.println("EMPLOYEE EMAIL : " + rs.getString(8));
            System.out.println("EMPLOYEE PHONE NO. : " + rs.getString(9));
            System.out.println("EMPLOYEE HIREDATE  : " + rs.getString(10));
            String sql2 = "SELECT d_name FROM Department WHERE d_id=?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setInt(1, rs.getInt(12));
            ResultSet rs2 = pst2.executeQuery();
            String dname = null;
            while (rs2.next()) {
                dname = rs2.getString(1);
            }
            System.out.println("EMPLOYEE DEPARTMENT NAME : " + dname);
            System.out.println("*****************************");

        }
        con.commit();
    }

    void createEmpdetailsFile(Connection con, String ans, int id) throws Exception {
        String sql = "SELECT * FROM Employee WHERE e_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            File f = new File("D://emp" + id + ".txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("Employee ID : " + rs.getInt(1));
            bw.newLine();
            bw.write("Employee First Name : " + rs.getString(2));
            bw.newLine();
            bw.write("Employee Last Name : " + rs.getString(3));
            bw.newLine();
            bw.write("Employee  Designation : " + rs.getString(4));
            bw.newLine();
            bw.write("Employee salary: " + rs.getDouble(5));
            bw.newLine();
            bw.write("Employee Age: " + rs.getInt(6));
            bw.newLine();
            bw.write("Employee Gender : " + rs.getString(7));
            bw.newLine();
            bw.write("Employee Email : " + rs.getString(8));
            bw.newLine();
            bw.write("Employee PhoneNO:" + rs.getString(9));
            bw.newLine();
            bw.write("Employee HireDate:" + rs.getString(10));
            bw.newLine();
            bw.write("Employee Department Id: " + rs.getInt(12));
            bw.newLine();
            bw.close();
        }
        System.out.println("FILE CREATED SUCCESSFULLY FOR THIS ID " + id);
    }

    void updateSalary(Connection con) throws Exception {
        con.setAutoCommit(false);
        System.out.print("ENTER ID YOU WANT TO UPDATE SALARY : ");
        int id = sc.nextInt();
        System.out.print("ENTER NEW SALARY : ");
        double newSalary = sc.nextDouble();
        String sql = "UPDATE employee set e_salary=? where e_id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setDouble(1, newSalary);
        pst.setInt(2, id);
        int rs = pst.executeUpdate();
        if (rs > 0) {
            String sql1 = "update salary set salaryAmount=? where e_id=?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setDouble(1, newSalary);
            pst1.setInt(2, id);
            pst1.executeUpdate();
            System.out.println("SALARY UPDATED SUCCESSFULLY.");
            con.commit();
        } else {
            System.out.println("NOT UPDATED.");
        }
    }

    void approveLeave(String answer, Connection con) throws Exception {
        con.setAutoCommit(false);
        System.out.print("ENTER ID FOR LEAVE APPROVAL : ");
        int id = sc.nextInt();
        String sql = "Update leave_status set status=? where e_id=?";
        PreparedStatement pst3 = con.prepareStatement(sql);
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("LEAVE IS APPROVED");
            pst3.setString(1, answer);
        } else {
            System.out.println("LEAVE IS DENIED");
            pst3.setString(1, answer);
        }
        pst3.setInt(2, id);
        pst3.executeUpdate();
        con.commit();
    }
}
