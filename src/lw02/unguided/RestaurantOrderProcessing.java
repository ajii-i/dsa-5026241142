import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class RestaurantOrderProcessing {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String[]> semuaPesanan = new LinkedList<>();
        LinkedList<String[]> stokMakanan = new LinkedList<>();
        LinkedList<String[]> stokMinuman = new LinkedList<>();
        LinkedList<String[]> pesananBerhasil = new LinkedList<>();

        Queue<String[]> antrean = new LinkedList<>();
        Stack<String[]> pesananGagal = new Stack<>();

        Scanner input = new Scanner(new File("orders.txt"));
        while (input.hasNext()) {
            String[] pesanan = {
                input.next(), 
                input.next(), 
                input.next(), 
                input.next()  
            };
            semuaPesanan.add(pesanan);
        }
        input.close();

        stokMakanan.add(new String[]{"Bakso", "2"});
        stokMakanan.add(new String[]{"Sate", "1"});
        stokMakanan.add(new String[]{"Soto", "2"});

        stokMinuman.add(new String[]{"EsTeh", "4"});
        stokMinuman.add(new String[]{"EsJeruk", "2"});

        for (String[] pesanan : semuaPesanan) {
            antrean.add(pesanan);
        }

        while (!antrean.isEmpty()) {
            String[] pesanan = antrean.poll();

            boolean makananTersedia = pesanan[1].equals("-");
            boolean minumanTersedia = pesanan[2].equals("-");
            int indeksMakanan = -1;
            int indeksMinuman = -1;

            if (!pesanan[1].equals("-")) {
                for (int i = 0; i < stokMakanan.size(); i++) {
                    String[] makanan = stokMakanan.get(i);

                    if (makanan[0].equals(pesanan[1])) {
                        indeksMakanan = i;
                        makananTersedia = Integer.parseInt(makanan[1]) > 0;
                        break;
                    }
                }
            }


            if (!pesanan[2].equals("-")) {
                for (int i = 0; i < stokMinuman.size(); i++) {
                    String[] minuman = stokMinuman.get(i);

                    if (minuman[0].equals(pesanan[2])) {
                        indeksMinuman = i;
                        minumanTersedia = Integer.parseInt(minuman[1]) > 0;
                        break;
                    }
                }
            }



            if (makananTersedia && minumanTersedia) {
                if (indeksMakanan != -1) {
                    String[] makanan = stokMakanan.get(indeksMakanan);
                    makanan[1] = String.valueOf(
                        Integer.parseInt(makanan[1]) - 1
                    );
                }

                if (indeksMinuman != -1) {
                    String[] minuman = stokMinuman.get(indeksMinuman);
                    minuman[1] = String.valueOf(
                        Integer.parseInt(minuman[1]) - 1
                    );
                }

                pesananBerhasil.add(pesanan);
            } else {
                pesananGagal.push(pesanan);
            }
        }



        System.out.println("=== Successfully Processed Orders ===");
        for (String[] pesanan : pesananBerhasil) {
            System.out.println(
                pesanan[0] + " " + pesanan[1] + " "
                + pesanan[2] + " " + pesanan[3]
            );
        }


        System.out.println();
        System.out.println("=== Remaining Food Stock ===");
        for (String[] makanan : stokMakanan) {
            System.out.println(makanan[0] + " : " + makanan[1]);
        }

        System.out.println();
        System.out.println("=== Remaining Drink Stock ===");
        for (String[] minuman : stokMinuman) {
            System.out.println(minuman[0] + " : " + minuman[1]);
        }


        System.out.println();
        System.out.println("=== Failed Orders ===");
        while (!pesananGagal.isEmpty()) {
            String[] pesanan = pesananGagal.pop();
            System.out.println(
                pesanan[0] + " " + pesanan[1] + " "
                + pesanan[2] + " " + pesanan[3]
            );
        }
    }
}







