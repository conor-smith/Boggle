package fdmBoggle.tournament;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import fdmBoggle.game.Boggle;
import fdmBoggle.ai.BoggleDictionary;

public class Tournament
{
    public static void runTournament(int noOfGames)
    {
        URLClassLoader classLoader = null;
        File playersDirectory =  new File(System.getProperty("user.home") + "/Documents/players");
        if(!playersDirectory.exists())
        {
            System.out.println("Players directory Documents/players does not exist");
            return;
        }
        File[] playerClassFiles = playersDirectory.listFiles();
        BogglePlayer[] players = new BogglePlayer[playerClassFiles.length];
        Boggle[] games = new Boggle[noOfGames];
        
        System.out.println("Loading players");
        
        try
        {
            
            classLoader = new URLClassLoader(new URL[] {new URL("file://" + System.getProperty("user.home") + "/Documents/players/")});
            for(int i = 0;i < playerClassFiles.length;i++)
            {
                try
                {
                    String className = playerClassFiles[i].getName().split("\\.")[0];
                    @SuppressWarnings("unchecked")
                    Class<BogglePlayer> playerClass = (Class<BogglePlayer>) classLoader.loadClass(className);
                    players[i] = playerClass.getDeclaredConstructor().newInstance();
                }
                catch(Exception e)
                {
                    String[] fullPath = playerClassFiles[i].getName().split("/");
                    System.out.println(fullPath[fullPath.length - 1] + " is either not a class file or does not meet tournament standards");
                    players[i] = null;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        
        System.out.println("Creating game boards.");
        
        BoggleDictionary.init();
        
        for(int i = 0;i < noOfGames;i++)
            games[i] = new Boggle(true);
        
        System.out.println("Beginning tournament");
        
        try
        {
            File resultsFile = new File(System.getProperty("user.home") + "/Documents/tournament.csv");
            FileWriter output = new FileWriter(resultsFile);
            if(!resultsFile.exists())
            {
                System.out.println("Creating results file at " + resultsFile);
                resultsFile.createNewFile();
            }
            output.write("Player, ");
            for(int i = 0;i < noOfGames;i++)
                output.write("Game" + i + ", , , ");
            output.write("\n , ");
            for(int i = 0;i < noOfGames;i++)
                output.write("score, time(milliseconds), score/time, ");
            output.write("\n");

            output.write("Default, ");

            for(Boggle game : games)
            {
                long[] answer = game.getTimeAndScore();
                output.write(answer[1] + ", " + answer[0] + ", " + (double)answer[1] / (double)answer[0] + ", ");
            }

            output.write("\n");
        
            for(BogglePlayer player : players)
            {
                try
                {
                    if(player != null)
                    {
                        output.write(player.toString() + ", ");

                        player.buildDictionary(new File(System.getProperty("user.home") + "/Documents/data/dict.txt"));
                        
                        for(Boggle game : games)
                        {
                            long startTime = System.currentTimeMillis();
                            player.playGame(game);
                            long endTime = System.currentTimeMillis();
                            long timeTaken = endTime - startTime;

                            output.write(game.getScore() + ", " + timeTaken + ", " + ((double)game.getScore() / (double)timeTaken) + ", ");
                            
                            System.out.println("Player " + player.toString() + " took " + timeTaken + " milliseconds to get " + game.getScore() + " points");
                            game.resetScoreAndGuesses();
                        }
                        
                        output.write("\n");
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Player " + player.toString() + " threw the following exception");
                    System.out.println(e.toString() + ": " + e.getMessage());
                    output.write("\n");
                }
            }

            output.close();
            classLoader.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }
}