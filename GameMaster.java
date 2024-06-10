import java.util.*;

public class GameMaster
{
    Scanner sc;
    List<Monster> monsters;
    List<Wizard> wizards;
    List<Wizard> wizardsNoAction;
    int monsterCount = 0;
    int wizardCount = 0;
    int setUnitCountState = 0;
    private final int maxWizardCount = 10;
    private final int maxMonsterCount = 10;
    public GameMaster()
    {
        sc = new Scanner(System.in);
        monsters = new ArrayList<>();
        wizards = new ArrayList<>();
        wizardsNoAction = new ArrayList<>();
        SetUnitCount();
        for(int i = 0, j = 0, k = 0 ; i < monsterCount; i++) {
            if (i % 2 == 0) {
                k++;
                monsters.add(new Monster("Orc" + "%d".formatted(k)));
            } else {
                j++;
                monsters.add(new Monster("Goblin" + "%d".formatted(j)));
            }
        }
        Collections.sort(monsters);
        for(int i = 0; i < wizardCount; i++)
        {
            wizards.add(new Wizard(i+1));
        }
        Collections.sort(wizards);
    }
    public void RemoveMonster(List<Monster> monsters)
    {
        for (int i = monsters.size()-1 ; i >= 0; i--)
        {
            if (monsters.get(i).GetHp() <= 0) {
                monsters.remove(i);
                monsterCount--;
            }
        }
        Collections.sort(this.monsters);
    }
    public void RemoveWizard(List<Wizard> wizards)
    {
        for (int i = wizards.size()-1 ; i >= 0; i--)
        {
            if (wizards.get(i).GetHp() <= 0) {
                wizards.remove(i);
                wizardCount--;
            }
        }
        Collections.sort(this.wizards);
    }
    public void MoveWizardToNoActionList()
    {
        Iterator<Wizard> iterator = wizards.iterator();
        while (iterator.hasNext())
        {
            Wizard wiz = iterator.next();
            if (!wiz.GetActionBool())
            {
                wizardsNoAction.add(wiz);
                iterator.remove();
            }
        }
    }
    public void MoveNoActionListToWizards()
    {
        for (Wizard wiz : wizardsNoAction)
        {
            wiz.SetActionTrue();
        }
        wizards.addAll(wizardsNoAction);
        wizardsNoAction.clear();
    }
    public void InsertWizard(int number)
    {
        for(int i = 0; i < number; i++)
        {
            wizards.add(new Wizard(i+wizardCount));
        }
        wizardCount += number;
        Collections.sort(this.wizards);
    }
    public void InsertMonster(int number)
    {
        for(int i = 0, j = 0, k = 0 ; i < number; i++)
        {
            if ( i % 2 == 0)
            {
                k++;
                monsters.add(new Monster("Orc"+"%d".formatted(k+monsterCount)));
            }
            else
            {
                j++;
                monsters.add(new Monster("Goblin"+"%d".formatted(j+monsterCount)));
            }
        }
        monsterCount += number;
        Collections.sort(this.monsters);
    }
    public void SetUnitCount() {
        switch (setUnitCountState) {
            case 0:
                try {
                    do {
                        System.out.println("마법사의 수와 몬스터의 수를 입력해 주세요.");
                        System.out.printf("마법사의 수(최대 %d) : ", maxMonsterCount);
                        this.wizardCount = sc.nextInt();
                    }
                    while (wizardCount <= 0 || wizardCount > maxWizardCount);
                    setUnitCountState = 1;
                } catch (InputMismatchException e) {
                    sc.nextLine();
                }

                SetUnitCount();
                break;
            case 1:
                try {
                    do {
                        System.out.println("몬스터의 수를 입력해 주세요.");
                        System.out.printf("몬스터의 수(최대 %d) : ", maxMonsterCount);
                        this.monsterCount = sc.nextInt();
                    }
                    while (monsterCount <= 0 || monsterCount > maxMonsterCount);
                    setUnitCountState = 2;
                } catch (InputMismatchException e) {
                    sc.nextLine();
                }

                SetUnitCount();
                break;
            case 2:
                System.out.printf("마법사 %d개 몬스터 %d개 생성하였습니다.\n", wizardCount, monsterCount);
        }

    }
    public int SelectWizard()
    {
        int selectWizard = 0;
        try
        {
            do {
                System.out.println("사용할 수 있는 마법사입니다.");
                for(int i = 0 ; i < wizards.size(); i++)
                {
                    System.out.printf("%d\t이름: %s\tHP: %d\tMP: %d\n"
                            ,i+1,wizards.get(i).GetName(),wizards.get(i).GetHp(),wizards.get(i).GetMp());
                }
                System.out.printf("1 부터 %d 까지 입력해 주세요\n",wizards.size());
                selectWizard = sc.nextInt();
            }while(selectWizard <= 0 || selectWizard > wizards.size() );
        }catch(Exception e)
        {
           sc.nextLine();
           SelectWizard();
        }

        return selectWizard-1;
    }

}
