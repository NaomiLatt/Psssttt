
public class TestValuesInRange 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		 ValuesInRange valuesInRange;

		  //  Validity check the constructor
		  try   { valuesInRange = new ValuesInRange ( 3, -3 ); }
		  catch ( IllegalArgumentException iae)  { display ( iae.getMessage() ); }


		  //  parameter test for set
		  try
		  {
		   valuesInRange = new ValuesInRange ( 3, 3 );
		   valuesInRange.setFor ( 2 );                 // value is outside the range
		  }
		  catch ( IllegalArgumentException iae)  { display ( iae.getMessage() ); }

		  //  parameter test for clear
		  try
		  {
		   valuesInRange = new ValuesInRange ( 3, 3 );
		   valuesInRange.clearFor ( 2 );                 // value is outside the range
		  }
		  catch ( IllegalArgumentException iae)  { display ( iae.getMessage() ); }


		  //  parameter test for get
		  try
		  {
		   valuesInRange = new ValuesInRange ( 3, 3 );
		   valuesInRange.contains ( 2 );                 // value is outside the range
		  }
		  catch ( IllegalArgumentException iae)  { display ( iae.getMessage() ); }


		  // test the set, clear, and contains methods
		  valuesInRange = new ValuesInRange ( 0, 100 );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   valuesInRange.setFor ( i );
		  }
		  valuesInRange.clearFor ( 71 );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   if ( !valuesInRange.contains ( i ) ) { display ( i + " is not in the bit map."); }
		  }

		  valuesInRange = new ValuesInRange ( -5000, 11700 );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   valuesInRange.setFor ( i );
		  }
		  valuesInRange.clearFor ( 71 );
		  valuesInRange.clearFor ( valuesInRange.getMinimumValueInRange() );
		  valuesInRange.clearFor ( valuesInRange.getMaximumValueInRange() );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   if ( !valuesInRange.contains ( i ) ) { display ( i + " is not in the bit map."); }
		  }

		  valuesInRange = new ValuesInRange ( -500000, 500000 );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   valuesInRange.setFor ( i );
		  }
		  valuesInRange.clearFor ( valuesInRange.getMinimumValueInRange() + 71 );
		  for (int i=valuesInRange.getMinimumValueInRange(); i<=valuesInRange.getMaximumValueInRange(); i++)
		  {
		   if ( !valuesInRange.contains ( i ) ) { display ( i + " is not in the bit map."); }
		  }


		 }  // main

		 public static void display ( String message ) { System.out.println ( message ); }

	}

//}
