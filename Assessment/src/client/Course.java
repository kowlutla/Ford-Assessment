package client;

public class Course {
    public Course(String title, int number, String abbre, int credit) {
        this.title = title;
        this.number = number;
        this.abbre = abbre;
        this.credit = credit;
    }

    private String title;
    private int number;
    private String abbre;
    private int credit;

    public String getAbbre() {
        return abbre;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", number=" + number +
                ", abbre='" + abbre + '\'' +
                ", credit=" + credit +
                '}';
    }

    public static void main(String[] args) {

        Course cs2=new Course("COSC",1437,"Programming Fundamentals II",4);
    }
}
