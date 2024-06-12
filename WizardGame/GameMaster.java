package WizardGame;

import java.util.*;

public class GameMaster
{
    Scanner sc;
    List<Monster> monsters;
    List<Wizard> wizards;
    int monsterCount = 0;
    int wizardCount = 0;
    int actionWizardCount = 0;
    int setUnitCountState = 0;
    private final int maxWizardCount = 9;
    private final int maxMonsterCount = 9;
    public GameMaster()
    {
        sc = new Scanner(System.in);
        monsters = new ArrayList<>();
        wizards = new ArrayList<>();
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
    }
//    public void MoveWizardToNoActionList()
//    {
//        Iterator<WizardGame.Wizard> iterator = wizards.iterator();
//        while (iterator.hasNext())
//        {
//            WizardGame.Wizard wiz = iterator.next();
//            if (!wiz.GetActionBool())
//            {
//                wizardsNoAction.add(wiz);
//                iterator.remove();
//            }
//        }
//    }
    public void SetActionTrue()
    {
        for (Wizard wiz : wizards)
        {
            wiz.SetActionTrue();
        }
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
        // 사용한 객체들은 삭제하고 다시 추가하여 제일 뒤로 보내고 얼마나 보냈는지 기록한다.
        // 반복문 횟수는 총 리스트의 크기에 사용한 갯수만큼 차감해 반복문을 적게 실행하게 만들어
        // 뒤로 간 객체들은 출력되지 않게 만든다.
        int selectWizard = 0;
        Wizard tempWizard = null;
          {

            do {
                try {
                        System.out.println("사용할 수 있는 마법사입니다.");
                        for (int i = 0; i < wizards.size() - actionWizardCount; i++)
                        {
                            if (!wizards.get(i).GetActionBool())
                            {
                                tempWizard = wizards.get(i);
                                wizards.remove(tempWizard);
                                wizards.add(tempWizard);
                                actionWizardCount++;
                            }
                        }
                        for (int i = 0; i < wizards.size() - actionWizardCount; i++)
                        {
                            System.out.printf("%d\t이름: %s\tHP: %d\tMP: %d\n"
                                    , i + 1, wizards.get(i).GetName(), wizards.get(i).GetHp(), wizards.get(i).GetMp());
                        }
                        System.out.printf("1 부터 %d 까지 입력해 주세요\n", wizards.size() - actionWizardCount);
                        selectWizard = sc.nextInt();
                        }
                catch (Exception e) {
                    selectWizard = 0;
                    sc.nextLine();
                }
            }while (selectWizard <= 0 || selectWizard > wizards.size() - actionWizardCount);
        }
        return selectWizard-1;
    }
    public void InsertWizard(int number)
    {
        for(int i = 0; i < number; i++)
        {
            wizards.add(new Wizard(i+wizardCount));
        }
        wizardCount += number;
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
    public void ActionWizardCountClear()
    {
        actionWizardCount = 0;
    }
}
