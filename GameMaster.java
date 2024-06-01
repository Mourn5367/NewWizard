import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameMaster
{
    Scanner sc;
    Wizard wizard;
    List<Monster> monsters;
    List<Wizard> wizards;
    int monsterCount = 9;
    int wizardCount = 2;
    public GameMaster()
    {
        sc = new Scanner(System.in);
        monsters = new ArrayList<>();
        wizards = new ArrayList<>();
        for(int i = 0, j = 0, k = 0 ; i < monsterCount; i++) {
            if (i % 2 == 0) {
                k++;
                monsters.add(new Monster("Orc" + "%d".formatted(k)));
            } else {
                j++;
                monsters.add(new Monster("Goblin" + "%d".formatted(j)));

            }
        }

        for(int i = 0; i < wizardCount; i++)
        {
            wizards.add(new Wizard(i));
        }
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
    }
}
