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
        //main.printTeachers();
        //main.cascadeTest();
        main.query2RemoveSchool();



        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
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

    private void printTeachers() {
        Query<Teacher> query = session.createQuery("from Teacher ", Teacher.class);
        List<Teacher> teachers = query.list();

        System.out.println("### Teachers");

        // 6
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
            for (SchoolClass schoolClass : teacher.getSchoolClass()) {
                System.out.println(schoolClass);
            }
        }

        System.out.println("### Teachers");
        System.out.println(" --- ");
        // 7
        Query<SchoolClass> query1 = session.createQuery("from SchoolClass", SchoolClass.class);
        List<SchoolClass> schools = query1.list();

        for (SchoolClass schoolClass : schools) {
            System.out.println(schoolClass);
            for (Teacher teacher : schoolClass.getTeachers()) {
                System.out.println( teacher);
            }
        }
    }


    private void addNewData() {
        School newSchool = new School();
        newSchool.setName("New School Name");
        newSchool.setAddress("ul. Nibylandia 23, 00-999 Krakow");

        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setProfile("mat-ing-geo");
        newSchoolClass.setCurrentYear(2020);
        newSchoolClass.setStartYear(2023);


        Student student0 = new Student();
        student0.setName("Janusz");
        student0.setSurame("Nowak");
        student0.setPesel("12345679");

        Student student1 = new Student();
        student1.setName("Tomasz");
        student1.setSurame("Nowak");
        student1.setPesel("90152336");

        Student student2 = new Student();
        student2.setName("Marysia");
        student2.setSurame("Wojciech-Nikt");
        student2.setPesel("987550036");

        newSchoolClass.addStudent(student0);
        newSchoolClass.addStudent(student1);
        newSchoolClass.addStudent(student2);

        newSchool.addSchoolClass(newSchoolClass);

        Transaction transaction = session.beginTransaction();
        session.save(newSchool); // gdzie newSchool to instancja nowej szkoły
        transaction.commit();

    }

    private void cascadeTest(){

        School newSchool = new School();
        newSchool.setName("New School Name");
        newSchool.setAddress("Aress 1");

        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setProfile("nowa klasa 1");
        newSchoolClass.setCurrentYear(2020);
        newSchoolClass.setStartYear(2023);

        SchoolClass newSchoolClass1 = new SchoolClass();
        newSchoolClass1.setProfile("nowa klasa 1");
        newSchoolClass1.setCurrentYear(2020);
        newSchoolClass1.setStartYear(2023);

        Student student0 = new Student();
        student0.setName("imie 1");
        student0.setSurame("nazwisko 1");
        student0.setPesel("12345679");

        Teacher t1 = new Teacher();
        t1.setName("imie 2");
        t1.setSurame("nazwisko");
        t1.setPesel("123546666");

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setProfile("nowa klasa 1");
        schoolClass.setCurrentYear(2020);
        schoolClass.setStartYear(2023);

        newSchoolClass.addTeacher(t1);
        t1.addSchoolClass(newSchoolClass);

        schoolClass.addTeacher(t1);

        newSchoolClass.addStudent(student0);
        newSchool.addSchoolClass(newSchoolClass1);


        Transaction transaction = session.beginTransaction();
        session.save(newSchool); // gdzie newSchool to instancja nowej szkoły
        session.save(t1);
        transaction.commit();


    }

    private void executeQueries() {
        //query0();
//		 query1();
    //    query2RemoveSchool();
//		 query3();
//		 query4();
//		 query5();
//		 query6();
    }

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

    private void query2RemoveSchool() {
        String hql = "from School s where s.name='New School Name'";
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

    /*private void query5() {
        String hql = "select count(s) from School s where size(s.classes) >= 2";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long schoolsCount = query.uniqueResult();
        System.out.println("Schools count: " + schoolsCount);
    }

    private void query6() {
        String hql = "select s from School s inner join s.classes c where c.profile = 'mat-fiz' and c.currentYear >= 2";
        Query<School> query = session.createQuery(hql, School.class);
        List<School> results = query.list();
        System.out.println(results);
    }*/

    private void updateObject() {
        Query<School> query = session.createQuery("from School where id = :id", School.class);
        query.setParameter("id", 2L);
        School school = query.uniqueResult();
        school.setAddress("Nowy adres2");

        Transaction transaction = session.beginTransaction();
        session.save(school);
        transaction.commit();
    }


}
