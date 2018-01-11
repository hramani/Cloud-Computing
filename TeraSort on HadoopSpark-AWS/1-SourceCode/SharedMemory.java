import java.io.*;
import java.util.*;

public class SharedMemory {

    public static class FileSort implements Runnable {
        private String fileName;

        FileSort(String fileName){
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                FileReader fr;
                BufferedReader br;
                String nLine;
                PrintWriter writer;
                FileOutputStream fos;
                PrintWriter out;
                List<String> sLines;
                ArrayList<String> bLines;
                out = new PrintWriter(new FileWriter("output.txt"));
                fos= new FileOutputStream("output//"+fileName);
                
                writer = new PrintWriter(fos);
                
                

                
                fr = new FileReader("input//" + fileName);
                
                br = new BufferedReader(fr);
                bLines = new ArrayList<String>();

                if ((nLine = br.readLine()) != null) {
                    do {

                        String key;
                        key = nLine.substring(0, 10);
                        String value;
                        value = nLine.substring(10, nLine.length());
                        bLines.add(key + value);
                    } while ((nLine = br.readLine()) != null);
                }

                br.close();

                
                sLines = doSort(bLines);

                for (Iterator<String> iterator = sLines.iterator(); iterator.hasNext(); ) {
                    String x = iterator.next();
                    writer.println(x);
                    out.println(x);
                }

                out.close();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        public static ArrayList<String> mergeSort(ArrayList<String> values) {
            ArrayList<String> l,r;
            l = new ArrayList<String>();
            r = new ArrayList<String>();
            int mid;

            if (values.size() != 1) {
                mid = values.size()/2;
                int i=0;

                while(true)
                {
                    if (!(i < mid)) break;
                    l.add(values.get(i));
                    i++;
                }
                int j=mid;
                if (j < values.size()) {
                    do {
                        r.add(values.get(j));
                        j++;
                    } while (j < values.size());
                }

                l  = mergeSort(l);
                r = mergeSort(r);

                merge(l, r, values);
            } else {
                return values;
            }
            return values;
        }

        private static void merge(ArrayList<String> l, ArrayList<String> r, ArrayList<String> values) {

            int lIndex,rIndex,valuesIndex,flagIndex;
            ArrayList<String> flag;
            lIndex= 0;
            rIndex = 0;
            valuesIndex = 0;

            if (lIndex < l.size() && rIndex < r.size()) {
                do {
                    if ((l.get(lIndex).compareTo(r.get(rIndex))) < 0) {
                        values.set(valuesIndex, l.get(lIndex));
                        lIndex++;
                    } else {
                        values.set(valuesIndex, r.get(rIndex));
                        rIndex++;
                    }
                    valuesIndex++;
                } while (lIndex < l.size() && rIndex < r.size());
            }

            if (lIndex >= l.size()) {
                flag = r;
                flagIndex = rIndex;
            } else {
                flag = l;
                flagIndex = lIndex;
            }
            int i=flagIndex;
            if (i < flag.size()) {
                do {
                    values.set(valuesIndex, flag.get(i));
                    valuesIndex++;
                    i++;
                } while (i < flag.size());
            }
        }

        private static ArrayList<String> doSort(ArrayList<String> lines) {


            ArrayList<String> ks = null,sLines = null,sKeys;
            String k,v;
            int count;
            Hashtable<String, String> values;
            values = new Hashtable<String, String>();

            ks = new ArrayList<String>();
            sLines = new ArrayList<String>();
            v = null;
            
            count = 0;

            for (Iterator<String> iterator = lines.iterator(); iterator.hasNext(); ) {
                String str = iterator.next();

                k = str.substring(0, 10);

                v = str.substring(10, str.length());

                ks.add(k);
                values.put(k, v);

                count++;
            }

            sKeys = mergeSort(ks);

            for (Iterator<String> iterator = sKeys.iterator(); iterator.hasNext(); ) {
                String x = iterator.next();
                String value = values.get(x);
                sLines.add(x + " " + value);
            }

            return sLines;
        }

    }



    private static void mergeChunks(List<String> files, String outputFile1) throws IOException
    {

        File fp1 = null;
        File fp2 = null;
        File fp3 = null;

        String in="sam",s11="";
        int num=0;
        PrintWriter output;
        output = null;
        String s12="",path = "./output//";

        if (files.size() > 1) {
            do {
                if (files.size() == 2) {
                    in = outputFile1;
                    fp3 = new File(in);
                } else {
                    in = "out-" + num++;
                    fp3 = new File(path + in);
                }

                String x1, x2;
                x1 = files.get(0);
                files.remove(0);

                x2 = files.get(0);
                files.remove(0);

                int i = 0;
                int j = 0;
                fp1 = new File(path + x1);
                fp2 = new File(path + x2);
                FileReader fr1;
                fr1 = new FileReader(fp1);
                FileReader fr2;
                fr2 = new FileReader(fp2);

                BufferedReader br1;
                br1 = new BufferedReader(fr1);

                BufferedReader br2;
                br2 = new BufferedReader(fr2);
                output = new PrintWriter(fp3);

                s11 = br1.readLine();
                i++;
                s12 = br2.readLine();
                j++;

                if (s11 != null && s12 != null) {
                    do {
                        if (s11.compareToIgnoreCase(s12) <= 0) {
                            output.println(s11);
                            s11 = br1.readLine();
                            i++;
                        } else {
                            output.println(s12);
                            s12 = br2.readLine();
                            j++;
                        }
                    } while (s11 != null && s12 != null);
                }
                if (s11 != null) {
                    output.println(s11);
                } else
                    output.println(s12);

                if ((s11 = br1.readLine()) != null) {
                    do {
                        output.println(s11);
                        i++;
                    } while ((s11 = br1.readLine()) != null);
                }

                if ((s12 = br2.readLine()) != null) {
                    do {
                        output.println(s12);
                        j++;
                    } while ((s12 = br2.readLine()) != null);
                }

                output.close();
                br1.close();
                br2.close();

                fr1.close();
                fr2.close();

                files.add(in);
            } while (files.size() > 1);
        }
    }

    private static List<String> fileSplit(String input, long buff) {

        ArrayList<String> files,files2;
        files = new ArrayList<String>();
        files2= new ArrayList<String>();

        try {
            int increment = 0;
            BufferedReader br;
            byte buffer[]= new byte[(int) buff];
            String line = "",file1 = "part-";
            
            br = new BufferedReader(new FileReader(new File(input)));

            FileInputStream fis;
            fis = new FileInputStream(input);


            File dic;
            dic = new File("input");

            if (line != null) {
                do {
                    long blockSize1;
                    blockSize1 = 0;
                    while (blockSize1 < buff && (line = br.readLine()) != null) {
                        files.add(line);
                        blockSize1 += line.length();
                    }
                    String flag1;
                    flag1 = file1 + increment;
                    files2.add(flag1);

                    int noOfChunks;
                    noOfChunks = increment;
                    FileWriter writer;
                    writer = null;
                    String folder;
                    folder = "input//";
                    File dir;
                    dir = new File(folder);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    int size;
                    size = files.size();
                    try {
                        writer = new FileWriter(folder + "part-" + increment);
                        for (String str : files) {
                            writer.write(str);
                            if (str.contains(" "))
                                writer.write("\n");
                        }
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    increment++;
                    files.clear();
                } while (line != null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files2;
    }

    public static void main(String[] args) {

        String i, o;
        int nTh = 0;

        System.out.println("|||||||||||||||||| Share Memory MergeSort |||||||||||||||||||||||||||||");

        i = "input.txt";

        o = "output.txt";
        nTh = 2;

        File fpx = new File(i);

        System.out.println("\nInput File: " + fpx.getName());
        System.out.println("Input File Size: " + (fpx.length()/1e9) + "GB");
        System.out.println("Number of Thread: " + nTh);

        System.out.println("\n ||||||||||||||||| Splitting File to Chunks ||||||||||||||||||||||");

        List<String> files = new ArrayList<String>();

        long blocksizex = 100*1024 ;

        long buffx = blocksizex;

        long st = System.currentTimeMillis();

        files = fileSplit(i, buffx);

        System.out.println("#### Done Splitting Files ####"+buffx);

        System.out.println("########## Number of chunks created: " + files.size());

        System.out.println("\n ||||||||||||| Sorting Chunks ||||||||||||||||||||");

        System.out.println("|||| Sorting Chunk by Chunk |||||");

        for (Iterator<String> iterator = files.iterator(); iterator.hasNext(); ) {
            String xx = iterator.next();
            FileSort thread = new FileSort(xx);
            (new Thread(thread)).start();
        }

        long et1 = 0;
        et1 = System.currentTimeMillis();
        System.out.println("Sorting Time : " + (et1 - st) + " ms");

        long et = 0;

        System.out.println("\n ||||||||||||||||||||||| Merging Sorted Chuncks |||||||||||||||||||||");
        try {
            mergeChunks(files, o);
            et = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long diff = et - st;

        System.out.println("#### Merge Completed  Input File sorted successfully ####");

        System.out.println("#### Total time for Sorting Input File: " + diff + " ms");

    }
}
