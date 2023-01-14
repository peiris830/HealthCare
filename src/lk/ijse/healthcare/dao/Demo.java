package lk.ijse.healthcare.dao;

interface GoodGirl{
    public void kissMe();
}

class Girl implements GoodGirl{
    @Override
    public void kissMe() {

    }
}
class Boy{public void kissGirl(){ while (true)
{
    GoodGirl g= new Girl();
    g.kissMe();
}}}

public class Demo {
    public static void main(String[] args) {
       new Boy().kissGirl();
    }
}
