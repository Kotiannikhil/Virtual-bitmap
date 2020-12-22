import java.util.*;
import java.io.*;
class vbitmap {
  public static int l,m,n;
  public static int[] rand;
  public static Map<String,Integer> hmap = new HashMap<>();
  public static Map<String,Integer> hmap1 = new HashMap<>();
  public static Map<String,Double> hmap2 = new HashMap<>();
  public static Map<String,Double> esize = new HashMap<>();
  public static int[] pbmap;
  public static void main(String[] args){
    try{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the value of l: ");
        l = sc.nextInt();
        System.out.println("Enter the value of m: ");
        m = sc.nextInt();
        File f = new File("project4input.txt");
        BufferedReader readline = new BufferedReader(new FileReader(f));
        String str = readline.readLine();
        n = Integer.parseInt(str);
        rand = new int[l];
        pbmap = new int[m];
        int i = 0,var,var1,low = 0,high = 10000;
        String[] line;
        Random r = new Random();
        Set<Integer> nn = new HashSet<>();
        while((str = readline.readLine()) != null){
          line = str.split("\\s+");
          var = r.nextInt(high - low) + 1;
          while(hmap.containsValue(var))
            var = r.nextInt(high - low) + 1;
          hmap.put(line[0],var);
          hmap1.put(line[0],Integer.parseInt(line[1]));
          if(i < 500){
            var1 = r.nextInt(high - low) + 1;
            while(nn.contains(var1)) {
              var1 = r.nextInt(high - low) + 1;
            }
            nn.add(var1);
            rand[i] = var1;
            i += 1;
          }
        }
        readline.close();
        for (String s:hmap.keySet()) {
          var = r.nextInt(high - low + 1);
          Set<Integer> hset = new HashSet<>();
          int[] vbmap = new int[l];
          for(int j = 0;j < hmap1.get(s);j++){
            var1 = r.nextInt(high - low + 1);
            if(hset.contains(var1)){
              j -= 1;
              continue;
            }
            hset.add(var1);
            vbmap[(var1 ^ var) % l] = 1;
            int temp = ((hmap.get(s) ^ rand[(var1 ^ var) % l]) ^ var) % m;
            pbmap[temp] = 1;
          }
          int count_vf = 0;
          for (int k = 0; k < l ; k++) {
            if(vbmap[k] == 0) {
              count_vf++;
            }
          }
          hmap2.put(s, (double)count_vf / l);
        }
        int countvb = 0,countvf = 0;
        double logvb = 0,e;
        for(int j = 0;j < m;j++){
          if(pbmap[j] == 0)
            countvb++;
        }
        logvb = l * (Math.log((double) countvb/m));
        for (String s : hmap2.keySet()) {
          double vf = hmap2.get(s);
          double logvf = (l * Math.log(hmap2.get(s)));
          esize.put(s,logvb - logvf);
        }
      BufferedWriter bwriter = new BufferedWriter(new FileWriter("vbitmap.txt"));
        for(String s:hmap.keySet()){
          bwriter.write(hmap1.get(s)+ "\t" +esize.get(s)+ "\n");
        }
        bwriter.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
