package pattern.core;

import pattern.models.Genome;
import pattern.util.Combine;

import java.util.*;

/**
 * Created by malam on 11/8/15.
 */
public class Main {

    private List<ArrayList<String>> kMers = new ArrayList<ArrayList<String>>();
    private char[] nucleotides = {'A', 'C', 'G', 'T'};
    public static List<HashSet<String>> PS = new ArrayList<HashSet<String>>();

    public void generateKmers() {
        ArrayList<String> temp = new ArrayList<String>();
        for (int k=1; k<4; k++) {
            Combine.printAllKLength(nucleotides, k, temp);
            kMers.add(new ArrayList<String>(temp));
            System.out.println("k = " + k + ", size = " + temp.size());
            temp.clear();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        String[] genomes = {"ataaaa", "atcgta", "atagcg"};

        List<Genome> S = new ArrayList<Genome>();
        for (String genome : genomes)
            S.add(new Genome(genome));

        main.generateKmers();
        StringBuilder genome = new StringBuilder();
        HashSet<String> temp = new HashSet<String>();

        for (int i=0; i<S.size(); i++) {
            genome.append(S.get(i).getName());
            for (int j=0; j<main.kMers.size(); j++) {
                if (j > genome.length())
                    break;
//                System.out.println("Genome : " + genome.toString() + ", kMers size " + main.kMers.size());
                for (String pattern : main.kMers.get(j)) {
//                    System.out.println("Looking for : " + pattern);
                    if (genome.indexOf(pattern.toLowerCase()) > -1)
                        temp.add(pattern);
                }
            }
            PS.add(new HashSet<String>(temp));
            temp.clear();
            genome.setLength(0);
        }

        HashSet<String> sum = new HashSet<String>();
        for (int i=0; i<S.size(); i++) {
            for (int j=0; j<S.size(); j++) {
                if (j == i)
                    continue;

                // this is the UNION operation on all PS(j) where j not equal to i
                sum.addAll(PS.get(j));
            }

            // this is the set DIFFERENCE operation to remove all elements from previous UNION
            PS.get(i).removeAll(sum);
            sum.clear();
        }

        for (int i=0; i<S.size(); i++) {
            System.out.print("Genome, S(" + i + ") : " + S.get(i).getName() + " - unique pattern(s) are { ");
            for (String unique : PS.get(i))
                System.out.print(unique + " ");
            System.out.println("}");
        }

    }
}
