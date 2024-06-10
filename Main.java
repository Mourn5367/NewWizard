import java.util.Random;
import java.util.Scanner;


public class Main
{

    public static void main(String[] args)
    {
        int state = 0;
        int turn = 1;
        boolean run = true;
        Random rand = new Random();
        GameMaster gameMaster = new GameMaster();
        Scanner sc = new Scanner(System.in);

        while(run)
        {
            switch (state)
            {
                // 마법사가 때리기
                case 0:
                    for (int i = 0; i < gameMaster.wizardCount; i++)
                    {
                        int indexWizard = gameMaster.SelectWizard();
                        int selectMon = gameMaster.wizards.get(indexWizard).SelectMonster(gameMaster,gameMaster.wizards.get(indexWizard));
                        gameMaster.wizards.get(indexWizard).Attack(gameMaster,gameMaster.monsters.get(selectMon-1),gameMaster.wizards.get(indexWizard));
                        gameMaster.RemoveMonster(gameMaster.monsters);
                        gameMaster.MoveWizardToNoActionList();
                        if (gameMaster.monsters.isEmpty())
                        {
                            System.out.println("적을 전부 죽였다.");
                            break;
                        }
                    }
                    if (gameMaster.monsters.isEmpty())
                    {
                        state = 3;
                        break;
                    }
                    else
                    {
                        state = 1;
                    }
                    break;
                    // 몬스터가 때리기
                case 1:
                    gameMaster.MoveNoActionListToWizards();
                    for (int i = 0 ; i< gameMaster.monsterCount; i++)
                    {
                        int randWizard = rand.nextInt(gameMaster.wizardCount);
//                        gameMaster.monsters.get(i).Attack(gameMaster.wizardsNoAction.get(randWizard));
//                        gameMaster.RemoveWizard(gameMaster.wizardsNoAction);
                        gameMaster.monsters.get(i).Attack(gameMaster.wizards.get(randWizard));
                        gameMaster.RemoveWizard(gameMaster.wizards);
                        if (gameMaster.wizards.isEmpty())
//                        if (gameMaster.wizardsNoAction.isEmpty())
                        {
                            System.out.println("플레이어 유닛이 전부 죽었다.\n");
                            break;
                        }
                    }
                    if (gameMaster.wizards.isEmpty())
//                    if (gameMaster.wizardsNoAction.isEmpty())
                    {
                        state = 4;
                        break;
                    }
                    state = 2;
                    break;
                case 2:
                    System.out.printf("%d번째 전투가 끝났다.\n",turn);
                    System.out.println("각 팀의 상태");
                    for (int i = 0; i < gameMaster.wizardCount; i++)
                    {
                        System.out.printf("이름: %s\tHP: %d\tMP: %d\n",
                                gameMaster.wizards.get(i).GetName(),
                                gameMaster.wizards.get(i).GetHp(),
                                gameMaster.wizards.get(i).GetMp());
                    }
                    System.out.println();
                    for (int i = 0; i < gameMaster.monsterCount; i++)
                    {
                        System.out.printf("이름: %s\tHP: %d\n",
                                gameMaster.monsters.get(i).GetName(),
                                gameMaster.monsters.get(i).GetHp());
                    }
                    System.out.println("\n\n");
                    turn++;
                    state = 0;
                    break;
                case 3:
                    System.out.printf("%d번째 전투에서 마법사의 승리로 끝이 났다.",turn);
                    run = false;
                    break;
                case 4:
                    System.out.printf("%d번째 전투에서 몬스터의 승리로 끝이 났다.",turn);
                    run = false;
                    break;
            }
        }
        if (state == 3)
        {
            System.out.println("승리");
        }
        else if (state == 4)
        {
            System.out.println("패배");
        }
    }
}
