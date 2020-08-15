package fdmBoggle.tournament;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import fdmBoggle.game.Boggle;
import fdmBoggle.ai.BoggleDictionary;

/**
 * Contains one static method to run tournament
 * Cannot be instantiated
 */
public class Tournament
{
    //Solely exists to prevent instantiation
    private Tournament()
    {}
    /**
     * Builds all players from class files which should be present at "$HOME/Documents/players"
     * Will abort if directory does not exist
     * Then creates a number of Boggle games specified by the entered parameter
     * One by one, makes each player play each game and records their results (time, score, score/time) in a csv file in Documents
     * Is able to handle exceptions during player class instantiation and while the player is playing the game
     * Also records the score for the WordGetter used to create the legalWords field in the boggle games
     * This is used as the "Default" player for all others to be compared to
     * @param noOfGames - The number of Boggle games to be played by each player
     */
    public static void runTournament(int noOfGames)
    {
        //Uses a URLClassLoader to load classes present at a file location
        URLClassLoader classLoader = null;
        File playersDirectory =  new File(System.getProperty("user.home") + "/Documents/players");
        if(!playersDirectory.exists())
        {
            //If the directory does not exist, exits method
            System.out.println("Players directory Documents/players does not exist");
            return;
        }
        //Grabs all files present in the players directory
        File[] playerClassFiles = playersDirectory.listFiles();
        BogglePlayer[] players = new BogglePlayer[playerClassFiles.length];
        
        System.out.println("Loading players");
        
        //This is used in case the classLoader throws an exception, at which point the method exits
        try
        {
            
            classLoader = new URLClassLoader(new URL[] {new URL("file://" + System.getProperty("user.home") + "/Documents/players/")});
            for(int i = 0;i < playerClassFiles.length;i++)
            {
                //This try/catch block is able to catch exception caused by the player not extending BogglePlayer, throwing an exception during instantiation, or if the file is corrupted / not a class file
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
        
        //Create the game boards
        Boggle[] games = new Boggle[noOfGames];
        System.out.println("Creating game boards.");
        
        //This is called here to ensure the default time is consistent for all boards
        BoggleDictionary.init();
        
        //All boards are created in tournament mode
        for(int i = 0;i < noOfGames;i++)
            games[i] = new Boggle(true);
        
        System.out.println("Beginning tournament");
        
        /*
         * This try/catch block handles errors relating to the results file
         * Exits if any are encountered
         */
        try
        {
            //Create/open results file
            File resultsFile = new File(System.getProperty("user.home") + "/Documents/tournament.csv");
            FileWriter output = new FileWriter(resultsFile);
            if(!resultsFile.exists())
            {
                System.out.println("Creating results file at " + resultsFile);
                resultsFile.createNewFile();
            }
            //Writes the column titles
            output.write("Player, ");
            for(int i = 0;i < noOfGames;i++)
                output.write("Game" + i + ", , , ");
            output.write("\n , ");
            for(int i = 0;i < noOfGames;i++)
                output.write("score, time(milliseconds), score/time, ");
            output.write("\n");

            output.write("Default, ");

            //Writes all default values
            for(Boggle game : games)
            {
                int defaultScore = game.getDefaultScore();
                long defaultTime = game.getDefaultTime();
                output.write(defaultScore + ", " + defaultTime + ", " + (double)defaultScore / (double)defaultTime + ", ");
            }

            output.write("\n");
        
            //Runs tournament with extensive error checking to ensure no player can derail tournament
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
                    e.printStackTrace();
                    output.write("ERROR\n");
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

    /**
     * Calls runTournament(int noOfGames) with the first entered value as noOfGames
     * @param args Should only contain one int value
     */
    public static void main(String[] args)
    {
        Tournament.runTournament(Integer.parseInt(args[0]));
    }
}