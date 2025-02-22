package pl.edu.agh.mwo.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();
        //main.addNewData();

        //main.printSchools();
        //main.printTeachersPoint6();
        //main.printTeachersPoint7();

        //main.cascadeTest();
        //main.executeQueries();

        // Test -> CascadeType.ALL
        //main.cascadeTest2();
        //main.executeQueries();

        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
    }

    // Lab 1 - 5
    private void executeQueries() {
        //query0();
        //query1();

        query2(); // delete shool
        deleteShoolClass(); // id = 4

        //query3();
        //query4();
        //query5();
        //query6();

        // Test -> CascadeType.ALL
        /*deleteShoolClass();
        query2a();*/
    }

    private void printSchools() {
        Query<School> query = session.createQuery("from School", School.class);
        List<School> schools = query.list();
        System.out.println("### Schools");
        for (School school : schools) {
            System.out.println(school);
            for (SchoolClass schoolClass : school.getSchoolClass()) {
                System.out.println(schoolClass);
                for (Student student : schoolClass.getStudents()) {
                    System.out.println(student);
                }
            }
        }
    }

    private void printTeachersPoint6() {
        Query<Teacher> query = session.createQuery("from Teacher ", Teacher.class);
        List<Teacher> teachers = query.list();

        System.out.println("### Teachers");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
            for (SchoolClass schoolClass : teacher.getSchoolClass()) {
                System.out.println(schoolClass);
            }
        }
    }

    private void printTeachersPoint7() {
        Query<Teacher> query = session.createQuery("from Teacher ", Teacher.class);
        List<Teacher> teachers = query.list();

        System.out.println("### Teachers");
        System.out.println(" --- ");
        Query<SchoolClass> query1 = session.createQuery("from SchoolClass", SchoolClass.class);
        List<SchoolClass> schools = query1.list();

        for (SchoolClass schoolClass : schools) {
            System.out.println(schoolClass);
            for (Teacher teacher : schoolClass.getTeachers()) {
                System.out.println(teacher);
            }
        }
    }

    private void deleteShoolClass() {
        String hql = "From SchoolClass c Where c.id=4";
        Query<SchoolClass> query = session.createQuery(hql, SchoolClass.class);
        SchoolClass schoolClass = query.uniqueResult();

        Transaction deleteTransaction = session.beginTransaction();
        for (Teacher teacher : schoolClass.getTeachers()) {
            teacher.removeSchoolClass(schoolClass);
            session.save(teacher);
        }
        session.delete(schoolClass);
        deleteTransaction.commit();
    }

    private void addNewData() {
        School newSchool = new School();
        newSchool.setName("Nowa szkoła");
        newSchool.setAddress("ul. Nibylandia 23, 00-999 Krakow");

        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setProfile("mat-ing-geo");
        newSchoolClass.setCurrentYear(1);
        newSchoolClass.setStartYear(2023);

        Student student0 = new Student();
        student0.setName("Janusz");
        student0.setSurame("Nowak");
        student0.setPesel("12345679");

        newSchoolClass.addStudent(student0);
        newSchool.addSchoolClass(newSchoolClass);

        Transaction transaction = session.beginTransaction();
        session.save(newSchool); // gdzie newSchool to instancja nowej szkoły
        transaction.commit();
    }

    private void cascadeTest() {
        School newSchool = new School();
        newSchool.setName("AKF");
        newSchool.setAddress("Adress 1");

        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setProfile("nowa klasa bio-chem");
        newSchoolClass.setCurrentYear(2);
        newSchoolClass.setStartYear(2023);

        Student student0 = new Student();
        student0.setName("Janusz");
        student0.setSurame("Chemik");
        student0.setPesel("12345999");

        newSchoolClass.addStudent(student0);
        //newSchool.addSchoolClass(newSchoolClass);

        Teacher teacher1 = new Teacher();
        teacher1.setName("Ziemowwit");
        teacher1.setSurame("Niski");
        teacher1.setPesel("123546666");

        newSchoolClass.addTeacher(teacher1);
        teacher1.addSchoolClass(newSchoolClass);
        newSchool.addSchoolClass(newSchoolClass);

        Transaction transaction = session.beginTransaction();
        //session.save(teacher1);
        session.save(newSchool); // gdzie newSchool to instancja nowej szkoły
        session.save(teacher1);
        transaction.commit();
    }

    private void cascadeTest2() {
        /* @ManyToMany(cascade = {CascadeType.ALL})
          @ManyToMany(mappedBy = "classes", cascade = {CascadeType.ALL})*/

        // 1
        School newSchool1 = new School();
        newSchool1.setName("ns_1");
        newSchool1.setAddress("Adress 1");

        SchoolClass newClass1 = new SchoolClass();
        newClass1.setProfile("nowa klasa szkoła 1");
        newClass1.setCurrentYear(2);
        newClass1.setStartYear(2023);

        Student student1 = new Student();
        student1.setName("uczen szkola 1");
        student1.setSurame("Chemik");
        student1.setPesel("12345999");

        newClass1.addStudent(student1);

        // 2
        School newSchool2 = new School();
        newSchool2.setName("ns_2");
        newSchool2.setAddress("Adress 2");

        SchoolClass newClass2 = new SchoolClass();
        newClass2.setProfile("nowa klasa szkoła 2");
        newClass2.setCurrentYear(2);
        newClass2.setStartYear(2023);

        Student student2 = new Student();
        student2.setName("uczen szkola 2");
        student2.setSurame("Chemik");
        student2.setPesel("12345999");

        newClass2.addStudent(student2);

        // ---
        Teacher teacher1 = new Teacher();
        teacher1.setName("t1");
        teacher1.setSurame("Niski");
        teacher1.setPesel("123546666");

        newClass1.addTeacher(teacher1);
        teacher1.addSchoolClass(newClass1);


        Teacher teacher2 = new Teacher();
        teacher2.setName("t2");
        teacher2.setSurame("t2");
        teacher2.setPesel("123546666");

        newClass2.addTeacher(teacher2);
        teacher2.addSchoolClass(newClass2);

        // ---
        newClass1.addTeacher(teacher2);
        teacher2.addSchoolClass(newClass1);

        newClass2.addTeacher(teacher1);
        teacher1.addSchoolClass(newClass2);

        newSchool1.addSchoolClass(newClass1);
        newSchool2.addSchoolClass(newClass2);


        Transaction transaction = session.beginTransaction();
        session.save(newSchool1);
        session.save(teacher1);
        session.save(newSchool2);
        session.save(teacher2);
        transaction.commit();
    }

    // Lab 1 - point 5
    private void query0() {
        String hql = "from School";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }

    private void query1() {
        String hql = "from School s where s.name='UE'";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }

    private void query2() {
        String hql = "from School s where s.name='AKF'";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (School s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    private void query3() {
        String hql = "select count(s) from School s";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Schools count: " + schoolsCount);
    }

    private void query4() {
        String hql = "select count(s) from Student s";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Students count: " + schoolsCount);
    }

    private void query5() {
        String hql = "select count(s) from School s where size(s.schoolClasses) >= 2";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Schools count: " + schoolsCount);
    }

    private void query6() {
        String hql = "select s from School s inner join s.schoolClasses c where c.profile = 'mat-fiz' and c.currentYear >= 2";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }

    private void updateObject() {
        Query<School> query = session.createQuery("from School where id = :id", School.class);
        query.setParameter("id", 2L);
        School school = query.uniqueResult();
        school.setAddress("Nowy adres2");

        Transaction transaction = session.beginTransaction();
        session.save(school);
        transaction.commit();
    }

    private void query2a() {
        String[] shoolNames = {"ns_1", "ns_2"};

        for (String name : shoolNames) {
            String hql = "from School s where s.name=" + "'" + name + "'";
            Query<School> query = session.createQuery(hql, School.class);
            List<School> results = query.list();

            Transaction transaction = session.beginTransaction();
            for (School s : results) {
                session.delete(s);
            }
            transaction.commit();
        }
    }
}
