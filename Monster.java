public class Monster
{
    private String name = "";
    private int hp;
    private int originDMG;

    public Monster(String name)
    {
        this.name = name;
        hp = 5;
        originDMG = 10;
    }

    public int GetDMG()
    {
        return originDMG;
    }

    public int GetHp()
    {
        return hp;
    }

    public void SetHp(int value)
    {
        hp -= value;
    }

    public void Attack(Wizard wizard)
    {
        wizard.SetHp(originDMG);
        System.out.println(name +"이(가)"+wizard.GetName()+"을(를) 공격하여"+ " 체력이" + wizard.GetHp()+" 되었습니다.");
        if (wizard.GetHp() <= 0)
        {
            System.out.println(wizard.GetName()+"의 목숨이 끊어졌다...!");
        }
    }

    public String GetName()
    {
        return name;
    }
}
