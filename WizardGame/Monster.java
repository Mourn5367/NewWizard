package WizardGame;

public class Monster implements Comparable<Monster>
{
    private String name = "";
    private int hp;
    private final int originDMG;

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
        System.out.println(name +"이(가)"+wizard.GetName()+"을(를) 공격하여"+ " HP가" + wizard.GetHp()+" 되었습니다.");
        if (wizard.GetHp() <= 0)
        {
            System.out.println(wizard.GetName()+"의 목숨이 끊어졌다...!");
        }
    }

    public String GetName()
    {
        return name;
    }

    // 몬스터의 순서를 이름순으로 오름차순 정렬하기 위해 Comparable 인터페이스를 상속받았다.
    @Override
    public int compareTo(Monster mon) {
        return this.name.compareTo(mon.name);
    }

}
