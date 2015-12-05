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
    public static List<HashSet<String>> finalPS = new ArrayList<HashSet<String>>();

    public void generateKmers() {
        ArrayList<String> temp = new ArrayList<String>();
        for (int k=1; k<4; k++) {
            Combine.printAllKLength(nucleotides, k, temp);
            kMers.add(new ArrayList<String>(temp));
//            System.out.println("k = " + k + ", size = " + temp.size());
            temp.clear();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        String[] genomes = {"ataaaa", "atcgta", "atagcg", "cgtaag", "tagcgt"};

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
                // We have 4 nucleotides, just checking to see if it's necessary to generate
                // k-mers of small value of k
                if (Math.pow(4,(j+1)) < genomes.length) {
                    continue;
                }
//                System.out.println("Genome : " + genome.toString() + ", kMers size " + main.kMers.size());
                for (String pattern : main.kMers.get(j)) {
//                    System.out.println("Looking for : " + pattern);
                    if (genome.indexOf(pattern.toLowerCase()) > -1)
                        temp.add(pattern);
                }
            }
            PS.add(new HashSet<String>(temp));
            finalPS.add(new HashSet<>(temp));
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
            finalPS.get(i).removeAll(sum);
            sum.clear();
        }

        Map<Integer, HashSet<String>> biomarkerCandidates = new HashMap<Integer, HashSet<String>>();
        for (int i=0; i<S.size(); i++) {
            System.out.print("Genome, S(" + i + ") : " + S.get(i).getName() + " - pattern(s) are { ");
            for (String unique : PS.get(i)) {
                System.out.print(unique + " ");
                if (biomarkerCandidates.get(unique.length()) == null)
                    temp.clear();
                else
                    temp = biomarkerCandidates.get(unique.length());
                temp.add(unique);
                biomarkerCandidates.put(unique.length(), new HashSet<String>(temp));
            }
            System.out.println("}");
        }

        temp.clear();
        ArrayList<String> biomarkers = new ArrayList<String>();
        boolean complete = false;
        int candidatesRequired = (int) Math.ceil(Math.log(genomes.length) / Math.log(2));

        StringBuilder sb = new StringBuilder();
        HashSet<Integer> uniqueDecimals = new HashSet<Integer>();

        for (Integer i : biomarkerCandidates.keySet()) {
            if (biomarkerCandidates.get(i).size() < candidatesRequired)
                continue;
            for (String gen : genomes) {
                System.out.print("Genome : " + gen + " ");
                for (String pattern : biomarkerCandidates.get(i)) {
                    if (gen.indexOf(pattern.toLowerCase()) > -1) {
                        System.out.print("(" + pattern + ") 1 ");
                        sb.append("1");
                    } else {
                        System.out.print("(" + pattern + ") 0 ");
                        sb.append("0");
                    }
                }
                System.out.print("Decimal Value = " + Integer.parseInt(sb.toString(), 2) + "\n");
                uniqueDecimals.add(Integer.parseInt(sb.toString(), 2));
                sb.setLength(0);
            }
            if (uniqueDecimals.size() == genomes.length) {
                System.out.println("Biomarkers are { " + String.join(", ", biomarkerCandidates.get(i)) + " }");
                break;
            }
        }

    }
}
