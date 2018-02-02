
import java.util.*;

public class BlackJack
{
	public static void main(String [] args)
	{
		//String rounds = args[0];
		//String decks = args[1];
		//String playerBank = args[2];
		int numRounds = Integer.parseInt(args[0]);
		int numDecks = Integer.parseInt(args[1]);
		int startingPlayerBank = Integer.parseInt(args[2]);
		int betAmount = Integer.parseInt(args[3]);

		System.out.println("Starting BlackJack with " + numRounds + " rounds and " + numDecks + " decks in the shoe");
		System.out.println("Players Starting Bank is $" + startingPlayerBank);
		System.out.println("Player will bet $" + betAmount + " every round");
		
		RandIndexQueue<Card> shoeCards = new RandIndexQueue<Card>(52*numDecks);

// create deck of cards

		int count =0;
		while(count<numDecks){

			for(int i=0; i<4; i++){
				for (int a=0; a<13; a++){
					Card c = new Card(Card.Suits.values()[i], Card.Ranks.values()[a]);
					shoeCards.offer(c);
				}
			}
			count++;
		}
// perform 3 shuffles
		shoeCards.shuffle();
		shoeCards.shuffle();
		shoeCards.shuffle();

		Boolean runHeadless = false;

		if(numRounds>10){
			runHeadless = true;
		}

		Game2 game = new Game2(numRounds, numDecks, startingPlayerBank, runHeadless, betAmount);

		game.playGame(shoeCards);

	}	

}