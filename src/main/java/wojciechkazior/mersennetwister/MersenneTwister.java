package wojciechkazior.mersennetwister;

public class MersenneTwister {

    private final int A = 			0b10011001000010001011000011011111; // 2567483615 // 0x9908B0DF
    private final int B = 			0b10011101001011000101011010000000; // 2636928640 // 0x9D2C5680
    private final int C = 			0b11101111110001100000000000000000; // 4022730752 // 0xEFC60000
    private final int D =			0b11111111111111111111111111111111; // 4294967295 // 0xFFFFFFFF
    private final int F = 			0b01101100000001111000100101100101; // 1812433253 // 0x6C078965
    private final int L = 			0b00000000000000000000000000010010; // 0000000018 // 0x00000012
    private final int M = 			0b00000000000000000000000110001101; // 0000000397 // 0x0000018D
    private final int N = 			0b00000000000000000000001001110000; // 0000000624 // 0x00000270
    private final int R = 			0b00000000000000000000000000011111; // 0000000031 // 0x0000001F
    private final int S = 			0b00000000000000000000000000000111; // 0000000007 // 0x00000007
    private final int T = 			0b00000000000000000000000000001111; // 0000000015 // 0x0000000F
    private final int U = 			0b00000000000000000000000000001011; // 0000000011 // 0x0000000B
    private final int W =			0b00000000000000000000000000100000; // 0000000032 // 0x00000020
    private final int MASK_LOWER = 	0b01111111111111111111111111111111; // 2147483647 // 0x7FFFFFFF
    private final int MASK_UPPER =	0b10000000000000000000000000000000; // 2147483648 // 0x80000000

    private int[] mt;
    private int index;

    public MersenneTwister(int seed) {
        mt = new int[N];
        index = N;
        mt[0] = seed;

        for (int i = 1; i < N; i++) {
            mt[i] = (F * (mt[i - 1] ^ (mt[i - 1] >>> 30)) + i);
        }
    }

    public int random() {
        int i = index;
        if (index >= N) {
            this.twist();
            i = index;
        }
        int y = mt[i];
        index = i + 1;

        y ^= (mt[i] >>> U);
        y ^= (y << S) & B;
        y ^= (y << T) & C;
        y ^= (y >>> L);

        return y;
    }

    private void twist() {
        int i, x, xA;
        for (i = 0; i < N; i++) {
            x = (mt[i] & MASK_UPPER) + (mt[(i + 1) % N] & MASK_LOWER);
            xA = x >>> 1;
            if ((x & 0x1) == 1) {
                xA ^= A;
            }
            mt[i] = mt[(i + M) % N] ^ xA;
        }
        index = 0;
    }
}