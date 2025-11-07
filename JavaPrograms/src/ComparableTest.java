import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Employee implements Comparable<Employee> {
    int age;
    int id;

    public Employee(int age, int id) {
        this.age = age;
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    // Natural ordering: id ascending, then age ascending
    @Override
    public int compareTo(Employee o) {
        int i = this.id - o.id;
        if (i == 0)
            return this.age - o.age;
        return i;
    }

    @Override
    public String toString() {
        return this.age + " " + this.id;
    }
}

// External Comparator classes
class AgeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Integer.compare(e1.getAge(), e2.getAge());
    }
}

class IdDescendingAgeAscendingComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        int idCompare = Integer.compare(e2.getId(), e1.getId()); // descending
        if (idCompare == 0) {
            return Integer.compare(e1.getAge(), e2.getAge()); // ascending
        }
        return idCompare;
    }
}

public class ComparableTest {
    public static void main(String[] args) {
        Employee e1 = new Employee(19, 1);
        Employee e2 = new Employee(20, 2);
        Employee e3 = new Employee(25, 1);

        ArrayList<Employee> arrayList = new ArrayList<>();
        arrayList.add(e1);
        arrayList.add(e2);
        arrayList.add(e3);

        System.out.println("Before Sorting:");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        // 1️⃣ Using Comparable (natural ordering)
        Collections.sort(arrayList);
        System.out.println("\nAfter Sorting (Comparable - by id then age):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        // 2️⃣ Using internal anonymous Comparator
        Collections.sort(arrayList, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        System.out.println("\nAfter Sorting (Anonymous Comparator - by age):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        // 3️⃣ Using internal lambda Comparator
        arrayList.sort((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        System.out.println("\nAfter Sorting (Lambda Comparator - by age):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        // 4️⃣ Using internal method reference Comparator
        arrayList.sort(Comparator.comparingInt(Employee::getAge));
        System.out.println("\nAfter Sorting (Method Reference Comparator - by age):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        // 5️⃣ Using external Comparator classes
        Collections.sort(arrayList, new AgeComparator());
        System.out.println("\nAfter Sorting (External Comparator - AgeComparator):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }

        Collections.sort(arrayList, new IdDescendingAgeAscendingComparator());
        System.out.println("\nAfter Sorting (External Comparator - IdDescAgeAsc):");
        for (Employee e : arrayList) {
            System.out.println(e);
        }
    }
}
