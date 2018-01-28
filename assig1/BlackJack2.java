
import java.util.*;

public class BlackJack2
{
	public static void main(String [] args)
	{

		String decks = args[0];

		int numDecks = Integer.parseInt(decks);

		System.out.println("There are " + decks + " decks in the shoe");

		System.out.println("Player, how much bank do you want to play with?");

		Scanner inScan = new Scanner(System.in);

		int userBank = inScan.nextInt();

		System.out.println("Player has $" +userBank + " to play with" );		

		
		RandIndexQueue<Card> shoeCards = new RandIndexQueue<Card>(52*numDecks);

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

		shoeCards.shuffle();
		shoeCards.shuffle();
		shoeCards.shuffle();


	//	for (int z=0; z<shoeCards.size(); z++){
	//		System.out.println("shoe cards = " + shoeCards.get(z));
	//	}




		RandIndexQueue<Card> dealerCards = new RandIndexQueue<Card>(1);

		dealerCards.offer(shoeCards.poll());

		//dealerCards.offer(shoeCards.poll());


		for (int x=0; x<dealerCards.size(); x++){
			System.out.println("dealer's cards = " + dealerCards.get(x) + " " +dealerCards.get(x).value());
		}


		RandIndexQueue<Card> playerCards = new RandIndexQueue<Card>(1);

		playerCards.offer(shoeCards.poll());

		playerCards.offer(shoeCards.poll());


		for (int y=0; y<playerCards.size(); y++){
			System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
		}


		System.out.println("Hit or Stay?");

		String response = inScan.next();

	

		if(response.toLowerCase().equals("hit")){
			System.out.println("Hitting");
		}
		else if(response.toLowerCase().equals("stay")){
			System.out.println("Staying");
		}





	}

}