// 2024_06_12_알고리즘_실습과제
// 한국폴리텍대학_서울정수캠퍼스_인공지능소프트웨어학과
// 2401110252_박지수
// 마법사로 몬스터를 물리치는 게임
package WizardGame;
import java.util.InputMismatchException;
import java.util.Random;
public class Wizard implements Comparable<Wizard>
{
    private String name = "";
    private int hp;
    private int mp;

    private final int originDMG;
    private final int magicDMG;
    private final int magicCost;
    private final int criticalPer;
    private final int criticalDMG;
    private final int actionList;
    private int state = 0;
    private int selectMonsterNum = 0;
    private final String[] actions;
    private boolean actionBool = true;
    Random rand = new Random();
    public Wizard()
    {
        name = "Gandalf";
        hp = 80;
        mp = 100;
        originDMG = 5;
        magicDMG = 30;
        magicCost = 30;
        criticalPer = 30;
        criticalDMG = 10;
        actions = new String[] { "1. 기본 공격","2. 마법 공격"};
        actionList = actions.length;
    }
    // 여러개를 생성할 경우 구분할수있게 이름에 숫자를 붙임
    public Wizard(int num)
    {
        this(); // 기본 생성자에 아래 내용을 추가 하겠다는 뜻.

        name += num;
    }
    public int SelectMonster(GameMaster gameMaster)
    {
        // 이 함수의 목적은 어떠한 몬스터를 공격할지 정하고 그 몬스터의 순서가 몇번째인지 반환하는 함수이다.
        // 처음 실행되면 state 변수가 0부터 시작하고 몬스터들의 정보를 출력한 다음 state 변수를 1로 바꾼다.
        // 그리고 이 함수를 다시 실행하는 재귀함수이다.
        // try catch를 통해서 정상적인 숫자값을 받도록 하고 부적격한 값을 얻을 경우 state 변수를 변경하지 않고
        // 다시 재귀하여 정상적인 값을 받아낼 때 까지 반복한다.
        // 정상적인 값을 받아낸다면 state 값을 0으로 다시 초기화 한 다음 함수를 탈출한다.
        switch(state)
        {
            case 0:
                System.out.println(this.GetName() +"의 차례이다.");

                for (int i = 0; i < gameMaster.monsters.size(); i++) {
                    System.out.printf("%d. 이름: %s  HP: %d\t", i + 1, gameMaster.monsters.get(i).GetName(), gameMaster.monsters.get(i).GetHp());
                    System.out.println();
                }
                state = 1;
                break;
            case 1:
                try
                {
                    do
                    {
                        System.out.printf("공격할 대상을 1 부터 %d 까지만 입력해 주세요\n",gameMaster.monsters.size());
                        selectMonsterNum = gameMaster.sc.nextInt();
                    }
                    while(selectMonsterNum <= 0 || selectMonsterNum > gameMaster.monsters.size());
                    state = 2;
                }
                catch(InputMismatchException e)
                {
                    gameMaster.sc.nextLine();
                }
                break;
        }
        if (state == 1)
        {
            SelectMonster(gameMaster);
        }
        else
        {
            state = 0;
        }
        return selectMonsterNum;
    }
public void Attack(GameMaster gameMaster,Monster monster)
{
    // 공격 방법을 정하고 그에 맞는 공격 함수를 실행한다. 여기서도 state 변수를 통해 행동을 제어하는데
    // 먼저 안내문이 끝나면 1로 변화하고 1에서 어떤 공격을 받을지에 따라 갈린다.
    // 일반공격이면 state 2로 간 다음 OriginAttack 함수를 실행하고 마법공격이면 state 3으로 가서 MagicAttack 함수를 실행한다.
    int selectAttack = 0;
    switch(state)
    {
        case 0:
            System.out.println(this.GetName() +"의 공격 방법은 ?");
            for (int i = 0 ; i < this.actions.length ; i++)
            {
                if ( i == 1)
                {
                    System.out.printf("%s(현재 MP%d)",this.actions[i],this.mp);
                }
                else
                {
                    System.out.printf("%s\t", this.actions[i]);
                }
            }
            System.out.println();
            state = 1;
            break;
        case 1:
            try
            {
                do {
                    System.out.printf("1 부터 %d 까지 입력해 주세요\n",this.actionList);

                selectAttack = gameMaster.sc.nextInt();
                }while(selectAttack <= 0 || selectAttack > this.actionList );
            }catch(InputMismatchException e)
            {
                gameMaster.sc.nextLine();
            }
            if (selectAttack == 1)
            {
                state = 2;
            }
            else if (selectAttack == 2)
            {
                state = 3;
            }
            break;
        case 2:
            OriginAttack(monster);
            System.out.printf("%s의 체력이 %d가 되었다..!\n", monster.GetName(), monster.GetHp());
            if (monster.GetHp() <= 0)
            {
                System.out.printf("%s은(는) 마침내 숨을 거두었다..!\n\n",monster.GetName());
            }
            state = 0;
            break;
        case 3:
            MagicAttack(monster);
            System.out.printf("%s의 체력이 %d가 되었다..!\n", monster.GetName(), monster.GetHp());
            if (monster.GetHp() <= 0)
            {
                System.out.printf("%s은(는) 마침내 숨을 거두었다..!\n\n",monster.GetName());
            }
            state = 0;
            break;
    }
    if (state != 0)
    {
        Attack(gameMaster, monster);
    }

    actionBool = false;
}
    public void OriginAttack(Monster monster)
    {
        System.out.println("일반공격");
        monster.SetHp(originDMG);
    }

    public void MagicAttack(Monster monster)
    {
        System.out.println("마법공격");
        if (mp >= magicCost)
        {
            SetMp(magicCost);
            if (rand.nextInt(101) < criticalPer)
            {
                System.out.println("크리티컬 발생 !");
                monster.SetHp(magicDMG + criticalDMG);
            }
            else
            {
                monster.SetHp(magicDMG);
            }
        }
        else
        {
            System.out.printf("가지고 있는 MP에 비해 요구량이 큰 공격을 사용하여 %s을(를) 사용합니다.\n",this.actions[0]);
            OriginAttack(monster);
        }
    }
    public int GetHp()
    {
        return hp;
    }

    public void SetHp(int value)
    {
        hp -= value;
    }
    public void SetMp(int value)
    {
        mp -= value;
    }
    public int GetMp()
    {
        return mp;
    }
    public String GetName()
    {
        return name;
    }
    public int GetActionList()
    {
        return actionList;
    }
    public void SetActionFalse()
    {
        actionBool = false;
    }
    public void SetActionTrue()
    {
        actionBool = true;
    }
    public boolean GetActionBool()
    {
        return actionBool;
    }


    // 마법사의 순서를 이름순으로 오름차순 정렬하기 위해 Comparable 인터페이스를 상속받았다.
    @Override
    public int compareTo(Wizard wiz)
    {
        return this.name.compareTo(wiz.name);
    }
}
