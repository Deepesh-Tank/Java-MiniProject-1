import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public int totalgender(FileResource fr,String gender){
    int totalnames = 0;
    for(CSVRecord csv : fr.getCSVParser(false)){
    if(csv.get(1).equals(gender)){totalnames++;}
    }
    return totalnames;
    }
    public void totalBirths(FileResource fr){
        int totalnames = 0;
        int f1 = 0;
        int f2 = 0;
        for(CSVRecord csv : fr.getCSVParser(false)){
            totalnames += 1;
            if(csv.get(1).equals("F")){
            if(f1==0){
            System.out.println("The name of girls are :-- ");
            System.out.println(csv.get(0));
            f1=1;
            }
            else{System.out.println(csv.get(0));}
            }
            else{
                if(f2==0){
                System.out.println("The name of boys are :-- ");
                System.out.println(csv.get(0));
                f2=1;
            }
            else{
                System.out.println(csv.get(0));}
            }
        }
        System.out.print("total names are ");
        System.out.println(totalnames);
    }
    public void test(){
    FileResource fr = new FileResource();
    totalBirths(fr);
    }
    public void testnumbers()
    {
        FileResource fr = new FileResource();
        System.out.println(totalgender(fr,"M"));
    }
    public int getRank(int year,String name,String gender){
        String f = "us_babynames_by_year/yob"+String.valueOf(year)+".csv";
        FileResource fr = new FileResource(f);
        int f1=0;
        int currrank = 0;
        int rank=-1;
        for(CSVRecord csv : fr.getCSVParser(false)){
            if(csv.get(1).equals(gender)){
                currrank += 1;
                if(csv.get(0).equals(name))
                {
                    rank = currrank;
                }
            }
        }
        return rank;
    }
    public void testgetRank()
    {
        System.out.println(getRank(1971,"Frank","M"));
        //System.out.println(getRank(2012,"Mason","F"));
    }
    public String getName(int year,int rank,String gender){
        String f = "us_babynames_by_year/yob"+String.valueOf(year)+".csv";
        FileResource fr = new FileResource(f);
        int currrank = 0;
        String ansString = "NO NAME";
        for(CSVRecord csv : fr.getCSVParser(false)){
            if(csv.get(1).equals(gender)){
                currrank += 1;
                if(currrank == rank)
                {
                    ansString = csv.get(0);
                }
            }
        }
        return ansString;
    }
    public void testgetName()
    {
        System.out.println(getName(1982,450,"M"));
    }
    public String whatIsNameInYear(String name,int year,int newYear,String gender){
        int rank = (getRank(year,name,gender));
        if(rank==-1)
        {
            return "INVALID NAME IN YEAR";
        }
        String newName = getName(newYear,rank,gender);
        if(newName.equals("NO NAME"))
        {
            return "NAME DIDNT EXSIST IN NEW YEAR";
        }
        else
        {
            return newName;
        }       
    }
    public void testwhatIsNameInYear(){
        System.out.println("Isabella born in year 2012 would be " + whatIsNameInYear("Owen",1974,2014,"M") + " in year 2014" );
    }
    public int yearOfHighestRank(String name,String gender){
        DirectoryResource dr = new DirectoryResource();
        int year = -1;
        int yearrank = -1;
        for(File f : dr.selectedFiles()){
            String years = f.getName();
            int index = years.indexOf("b");
            String tempyear = years.substring(index+1,index+5);
            int temprank = getRank(Integer.parseInt(tempyear),name,gender);
            if(temprank!=-1){
                if(yearrank!=-1){
                    if(yearrank > temprank)
                    {
                        yearrank = temprank;
                        year =Integer.parseInt(tempyear);
                    }
                }
                else
                {
                    yearrank = temprank;
                    year =Integer.parseInt(tempyear);
                }
            }
            
        }
        return year;
    }
    public void testyearOfHighestRank(){
        System.out.println(yearOfHighestRank("Mich","M"));
    }
    public double getAverageRank(String name,String gender){
        DirectoryResource dr = new DirectoryResource();
        int fl = 0;
        int ranksum = 0;
        int count=0;
        for(File f : dr.selectedFiles()){
            String filename = f.getName();
            int index = filename.indexOf("b");
            String tempyear = filename.substring(index+1,index+5);
            int temprank = getRank(Integer.parseInt(tempyear),name,gender);
            if(temprank!=-1)
            {
                fl =1;
                count += 1;
                ranksum += temprank;
            }
            
        }
        if(fl == 0){
        return -1.0;}
        else{
        return (double)ranksum/count;}
    }
    public void testgetAverageRank()
    {
        System.out.println(getAverageRank("Susan","F"));
        System.out.println(getAverageRank("Robert","M"));
    }
    public int getTotalBirthsRankedHigher(int year,String Name,String gender){
        int rank = getRank(year,Name,gender);
        int currrank=0;
        int total = 0;
        String f = "us_babynames_by_year/yob"+String.valueOf(year)+".csv";
        FileResource fr = new FileResource(f);
        CSVParser parser = fr.getCSVParser(false);
        for(CSVRecord csv : parser){
            if(csv.get(1).equals(gender)){
                 currrank += 1;
                if(currrank<rank)
                {
                    total += Integer.parseInt(csv.get(2));
                }
            }
        }
        return total;
    }
    public void testgetTotalBirthsRankedHigher()
    {
        System.out.println(getTotalBirthsRankedHigher(1990,"Emily","F"));
        System.out.println(getTotalBirthsRankedHigher(1990,"Drew","M"));
    }
}
