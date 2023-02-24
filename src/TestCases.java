import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestCases
{
   private static Student[] studentArray = new Student[] {
           new Student("Smidt", "Bob", 26, 2.67),
           new Student("Workman", "Julie", 21, 3.84),
           new Student("Other", "Julie", 20, 3.84),
           new Student("Johnson", "Jane", 18, 3.59),
           new Student("Hatalsky", "Paul", 22, 3.21),
           new Student("Wood", "Zoe", 21, 3.62)
   };
      
   @Test
   public void testAgePred()
   {
      // return true if age > 21
      // ex 1: using lambda (cannot use method reference operator here)
      Predicate<Student> pred = s -> s.getAge() > 21;

      assertTrue(pred.test(studentArray[0]));
   }

   @Test
   public void testNameFunc()
   {
      // Input: Student, Return Type: String
      // ex 1: using lambda
      Function<Student, String> func = s -> s.getFirstName();
      // ex 2:using  method reference operator
      Function<Student, String> func2 = Student::getFirstName;

      assertEquals(func.apply(studentArray[1]), "Julie");
      assertEquals(func2.apply(studentArray[1]), "Julie");
   }
   
   @Test
   public void testGpaComp()
   {
      // ex 1: using lambda
      Comparator<Student> compGpa = (s1, s2) -> ((Double)s1.getGpa()).compareTo(s2.getGpa());

      // ex 2: using Comparator.comparing (with lambda for Function)
      Comparator<Student> compGpa2 = Comparator.comparing(s -> s.getGpa());

      // ex 3: using Comparator.comparing (with method reference operator for Function)
      Comparator<Student> compGpa3 = Comparator.comparing(Student::getGpa);

      assertTrue(compGpa.compare(studentArray[0], studentArray[1]) < 0);
      assertTrue(compGpa2.compare(studentArray[0], studentArray[1]) < 0);
      assertTrue(compGpa3.compare(studentArray[0], studentArray[1]) < 0);
   }

   @Test
   public void testNameThenAgeComp()
   {
      // Using Lambdas
      Comparator<Student> compName = (s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName());
      Comparator<Student> compAge = (s1, s2) -> s1.getAge() - s2.getAge();
      Comparator<Student> compCombined = compName.thenComparing(compAge);
      // Note: cannot call .thenComparing on something that is not declared as a comparator
      // (i.e. cannot call on just a lambda)
      // Comparator<Student> compCombined2 = (s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName()).thenComparing(compAge);

      // You can pass in a lambda to .thenComparing since the parameter type is declared:
      Comparator<Student> compCombined3 = compName.thenComparing((s1, s2) -> s1.getAge() - s2.getAge());

      assertTrue(compCombined.compare(studentArray[0], studentArray[1]) < 0);
      assertTrue(compCombined.compare(studentArray[1], studentArray[2]) > 0);
      assertTrue(compCombined3.compare(studentArray[1], studentArray[2]) > 0);
   }

   @Test
   public void testAgeReversedComp()
   {
      // Using lambda with reversed logic
      // Note: you cannot call .reversed() on a lambda.  Only on something stored as a comparator
      Comparator<Student> compAgeReversed = (s1, s2) -> s2.getAge() - s1.getAge();
      assertTrue(compAgeReversed.compare(studentArray[0], studentArray[1]) < 0);

      // Using lambda with reversed() helper
      Comparator<Student> compAge = (s1, s2) -> s1.getAge() - s2.getAge();
      assertTrue(compAge.reversed().compare(studentArray[0], studentArray[1]) < 0);
   }


   @Test
   public void testGpaReversedThenAgeComp()
   {
      // ex 1: using lambda
      Comparator<Student> compGpa = (s1, s2) -> ((Double)s1.getGpa()).compareTo(s2.getGpa());

      // ex 2: thenComparing with method reference operator
      Comparator<Student> compTotal = compGpa.reversed().thenComparing(Student::getAge);

      assertTrue(compTotal.compare(studentArray[0], studentArray[1]) > 0);
   }



}
