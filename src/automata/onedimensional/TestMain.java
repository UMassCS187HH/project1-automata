package automata.onedimensional;

public class TestMain
{
	public static void main(String[] args)
	{
		BitArrayNoBP arraynbp1 = new BitArrayNoBP(80), arraynbp2 = new BitArrayNoBP(80);
		arraynbp1.set(40, 1L);
		BitArray array1 = new BitArray(80), array2 = new BitArray(80);
		array1.set(40, true);
		long bpavg = 0, nobpavg = 0;
		for (int count = 0; count < 100; count++)
		{
			long t1, t2, t3;
			t1 = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++)
			{
				Automata.evolve(array1, array2, 110);
				Automata.evolve(array2, array1, 110);	
			}
			t2 = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++)
			{
				Automata.evolve(arraynbp1, arraynbp2, 110);
				Automata.evolve(arraynbp2, arraynbp1, 110);
			}
			t3 = System.currentTimeMillis();
			bpavg += t2-t1;
			nobpavg += t3-t2;
		}
		
		System.out.println("With branch prediction, time took:    " + Math.round(((double)bpavg) / 100));
		System.out.println("Without branch prediciton, time took: " + Math.round(((double)nobpavg) / 100));
		System.out.println(array1);
		System.out.println(arraynbp1);
	}
}
