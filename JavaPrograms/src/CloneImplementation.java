/*
==================== CLONEABLE INTERFACE & OBJECT.CLONE() ====================

1️⃣ Cloneable Interface
- Marker interface (no methods).
- Signals that the class allows cloning via Object.clone().
- If not implemented, calling super.clone() throws CloneNotSupportedException at runtime.

2️⃣ Object.clone() method
- Protected native method in java.lang.Object:
      protected native Object clone() throws CloneNotSupportedException;
- Performs shallow copy of the object.
- Does NOT call constructors.
- Checks at runtime if object implements Cloneable:
      if (!(this instanceof Cloneable)) throw CloneNotSupportedException();

3️⃣ Overriding clone()
- Typically overridden to make it public and return class type:
      @Override
      public MyClass clone() throws CloneNotSupportedException {
          return (MyClass) super.clone();
      }

4️⃣ Shallow Copy
- Copies top-level fields.
- Nested objects (references) are shared between original and clone.
- Default behavior of Object.clone().
- Example:
      class Employee implements Cloneable {
          int id;
          Department dept;
          public Employee clone() throws CloneNotSupportedException {
              return (Employee) super.clone(); // shallow copy
          }
      }
- Changing dept in clone affects original.

5️⃣ Deep Copy
- Creates independent clone including nested objects.
- Must manually clone nested objects or use copy constructors/serialization.
- Example using new temp object for nested field:
      class Employee implements Cloneable {
          int id;
          Department dept;

          @Override
          public Employee clone() throws CloneNotSupportedException {
              Employee e = (Employee) super.clone();       // shallow copy
              e.dept = new Department(this.dept.name);     // deep copy
              return e;
          }
      }
- Now modifying e.dept.name does NOT affect original object.

6️⃣ Manual clone (without super.clone())
- If you override clone() and don’t call super.clone(), JVM’s internal Cloneable check is skipped.
- You can manually create and return a new object with copied fields.
- Example:
      @Override
      public MyClass clone() {
          return new MyClass(this.field1, this.field2);
      }

7️⃣ Key Points Summary
- Cloneable → marker interface, enables Object.clone().
- clone() → shallow copy by default.
- Deep copy → manually clone nested objects.
- JVM checks Cloneable at runtime in Object.clone() using instanceof.
- Overriding clone() gives flexibility to make it public and return your type.
- Using temp objects for deep copy prevents shared references.
*/


class CloneableClassExample implements Cloneable{
    int i;
    int j;

    CloneableClassExample(){}
    CloneableClassExample(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return ("i -> " + i + " j -> " + j );
    }
}
public class CloneImplementation {
    public static void main (String[] args) throws CloneNotSupportedException {

        /**Shallow Copy
         * IN SHALLOW COPY ONLY REFERENCE IS CREATED THE OBJECT REMAINS SAME, SO BIG PROBLEM IS THAT
         * IF WE CHANGE IN OBJ2 VALUE WILL BE CHANGED IN OBJ1 AS WELL
         */
        CloneableClassExample obj1 = new CloneableClassExample(2, 6);
        CloneableClassExample obj2 = obj1;

        obj2.i = 4;

        System.out.println("obj2 ->" + obj2);
        System.out.println("obj1 ->" + obj1);

        /**Deep Copy
         * VIA DEEP COPY WE HAVE TO MANUALLY CREATE A NEW OBJECT AND COPY ALL THE VARIABLES FROM DESIRED OBJECT,
         * THIS SOLVES ISSUE IN SHALLOW COPY BUT IS INEFFECTIVE IN CASE THERE ARE MANY VARIABLES
         */

        CloneableClassExample obj3 = new CloneableClassExample();
        obj3.i = obj1.i;
        obj3.j = obj1.j;

        obj3.j = 10;

        System.out.println("obj3 -> " + obj3);
        System.out.println("obj1 -> " + obj1);

        /**
         * VIA CLONE WE SOLVE BOTH THE PROBLEMS FROM ABOVE BY SIMPLY CALLING CLONE METHOD WE CAN
         * CLONE THE OBJECT BUT IT HAS FEW POINTS TO REMEMBER
         *
         * SINCE CLONE IN OBJECT HAS PROTECTED ACCESS SO WE NEED TO OVERRIDE AND REFER THE METHOD USING SUPER,
         * AFTER THAT WE NEED TO ADD THE CLONE NOT SUPPORTED EXCEPTION, NOW SINCE BECAUSE OF SECURITY ISSUES TO PREVENT
         * IT'S USAGE WE HAVE TO IMPLEMENT CLONEABLE MARKER INTERFACE
         */

        CloneableClassExample obj4 = (CloneableClassExample) obj1.clone();

        System.out.println("obj4 -> " + obj4);

    }
}
