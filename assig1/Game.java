public class Game{

	int playerWins, playerBUSTED;
	int dealerWins, dealerBUSTED;
	int pushes;
	int rounds;
	int playerBlackJack;
	int dealerBlackJack;
	int playerBank;

	int totalCards = 0;

	RandIndexQueue<Card> discard = new RandIndexQueue<>(2);

	public Game(int rounds, int playerBank, int decks){
		// initialize to zero and get rounds
		playerWins= 0;
		dealerWins= 0;
		pushes= 0;

		playerBlackJack = 0;
		dealerBlackJack = 0;

		playerBUSTED = 0;
		dealerBUSTED = 0;
		this.rounds = rounds;
		this.playerBank = playerBank;

		totalCards = decks*52;
	}

	public void playGame(RandIndexQueue<Card> theShoe){
		System.out.println("you made it to playGame");
		// play as many rounds as passed from original argument
		

		for (int i=0; i<rounds; i++){
			System.out.println("########## Start of Round " + i +  "##########");

			// if theSHoe is less than 25% clear, push all discard back into theSHoe and shuffle
			if(((double) theShoe.size() / (double) totalCards) < .25){
				// put dicard back in shoe
				// shuffle

				System.out.println("should only be less than .25" + ((double) theShoe.size()/ (double) totalCards));

				System.out.println("#*#*#*#*# RESHUFFLING THE SHOE IN ROUND " + i + " #*#*#*#*#");

				for (int ii=0; ii<discard.size(); ii++){
					theShoe.offer(discard.poll());
				}
			}

			playRound(theShoe);
			int tmp = theShoe.size();
			System.out.println("theShoe size = " + tmp);
		}


		System.out.println("PUSHES = " + pushes);

		System.out.println("Player BlackJack = " + playerBlackJack);
		System.out.println("Dealer BlackJack = " + dealerBlackJack);

		System.out.println("Player Wins = " + playerWins);
		System.out.println("Dealer Wins = " + dealerWins);

		System.out.println("Player Busts = " + playerBUSTED);
		System.out.println("Dealer Busts = " + dealerBUSTED);
// print whast in discard
		//for (int y=0; y<discard.size(); y++){
		//	System.out.println("discard cards = " + discard.get(y) + " " +discard.get(y).value());
			//playerHand+=playerCards.get(y).value();

		//	}

	}

	public void playRound(RandIndexQueue<Card> theShoe){

/////// every round should start this way

		RandIndexQueue<Card> dealerCards = new RandIndexQueue<Card>(1);
		RandIndexQueue<Card> playerCards = new RandIndexQueue<Card>(1);

		playerCards.offer(theShoe.poll());

		dealerCards.offer(theShoe.poll());

		playerCards.offer(theShoe.poll());

		dealerCards.offer(theShoe.poll());	



		int dealerHandStart = 0;
		int playerHandStart = 0;

		for (int yzzzy=0; yzzzy<dealerCards.size(); yzzzy++){
		//	System.out.println("dealers's cards = " + dealerCards.get(yzzzy) + " " +dealerCards.get(yzzzy).value());
			dealerHandStart+=dealerCards.get(yzzzy).value();

			}

		for (int yzzz=0; yzzz<playerCards.size(); yzzz++){
		//	System.out.println("players's cards = " + playerCards.get(yzzz) + " " +playerCards.get(yzzz).value());
			playerHandStart+=playerCards.get(yzzz).value();

			}


		if(dealerHandStart==21 && playerHandStart==21){
			pushes++;
			return;
		}	

		if(playerHandStart==21 && dealerHandStart<21){
			System.out.println("___PLAERYBLACKJACK___");
			playerWins++;
			playerBlackJack++;
			return;
		}

		if(dealerHandStart==21 && playerHandStart<21){
			System.out.println("___DEALERBLACKJACK___");
			dealerWins++;
			dealerBlackJack++;
			return;
		}



		System.out.println("***dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

		boolean nextCard = true;

		boolean playerBusted = false;
		boolean dealerBusted = false;

		while(nextCard && playerShouldHit(dealerCards, playerCards)){
			System.out.println("****Hitting****");

			playerCards.offer(theShoe.poll());

			if(busted(playerCards)){
					System.out.println("************* PLAYER BUSTED ***********");
					// dealerwins if player busts
					playerBusted = true;
					playerBUSTED++;
					dealerWins++;
					nextCard = false;

				}

			if(nextCard && playerShouldHit(dealerCards, playerCards)){
				
				nextCard = true;
			}
		
			nextCard = false;
			
			

		}






		
		int playerHand = 0;
		int playerAces = 0;


		for (int y=0; y<playerCards.size(); y++){
			System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(y).value();
			if(playerCards.get(y).value()==11){
				playerAces++;
				}

			}


		while(playerHand>21 && playerAces>0){
			// use ace as one instead of 11... not subtract 10....
			playerHand-=10;
			playerAces--;
		}
		System.out.println("****Staying****");	
		System.out.println("PlayerHand = " + playerHand);	

	//	int dealerHand = 0;

	//	for (int yyy=0; yyy<dealerCards.size(); yyy++){
	//		System.out.println("dealer's cards = " + dealerCards.get(yyy) + " " +dealerCards.get(yyy).value());
	//		dealerHand+=dealerCards.get(yyy).value();
	//		}


		boolean nextCard2 = true;

		while(nextCard2 && dealerShouldHit(dealerCards, playerCards)){
			System.out.println("****Hitting****");

			dealerCards.offer(theShoe.poll());

			if(busted(dealerCards)){
					System.out.println("************* DEALER BUSTED ***********");
					dealerBusted = true;
					dealerBUSTED++;
					// dplayererwins if dealer busts
//					playerWins++;
					nextCard2 = false;

				}

			if(nextCard2 && dealerShouldHit(dealerCards, playerCards)){
				
				nextCard2 = true;
			}
		
			nextCard2 = false;
			
			

		}

		int dealerHand = 0;
		int dealerAces = 0;

		for (int yyy=0; yyy<dealerCards.size(); yyy++){
			System.out.println("dealer's cards = " + dealerCards.get(yyy) + " " +dealerCards.get(yyy).value());
			dealerHand+=dealerCards.get(yyy).value();
			
			if(dealerCards.get(yyy).value()==11){
				dealerAces++;
				}
			}

		while(dealerHand>21 && dealerAces>0){
			// use ace as one instead of 11... not subtract 10....
			dealerHand-=10;
			dealerAces--;
		}


		System.out.println("****Staying****");	
		System.out.println("Dealer Hand = " + dealerHand);	







/// determining who wont he round

		if(((playerHand > dealerHand) && !playerBusted) || dealerBusted){
			playerWins++;
		}
		else if (playerHand == dealerHand){
			pushes++;
		}
		else{
			dealerWins++;
		}
		



		//System.out.println("***dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

		for (int xx=0; xx<playerCards.size(); xx++){
			discard.offer(playerCards.get(xx));

			}
		for (int yy=0; yy<dealerCards.size(); yy++){
			discard.offer(dealerCards.get(yy));

			}

	}

	public Boolean playerShouldHit(RandIndexQueue<Card> dealerCards, RandIndexQueue<Card> playerCards){

		boolean shouldHitIt = true;

		int playerHand=0;

		for (int y=0; y<playerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(y).value();
		}

	//	System.out.println("PlayerHand total= " + playerHand);

		//
		//System.out.println("dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

	//	System.out.println("playerHand count = " + playerHand);

		int dealerHas = dealerCards.get(1).value();

	//	System.out.println("dealer has  = " + dealerHas);		

////////////////////////////////////////////////////////////////////////////////
/////////////////// adjust hitting rules here ////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

		////////////
		// need to know if should turn Ace into 11 or 1 here....
		////////////

		if(playerHand>16){
			shouldHitIt = false;
		}

		if(dealerHas<6 && playerHand>=12){
			shouldHitIt = false;
		}

		if(dealerHas>6 && playerHand<16){
			shouldHitIt = true;
		}

		return shouldHitIt;

}


	public Boolean dealerShouldHit(RandIndexQueue<Card> dealerCards, RandIndexQueue<Card> playerCards){

		boolean shouldHitIt = false;

		int dealerHand=0;

		for (int y=0; y<dealerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			dealerHand+=dealerCards.get(y).value();
		}


		System.out.println("CURRENT DEALER HAND = " + dealerHand);


		int playerHand=0;

		for (int yz=0; yz<playerCards.size(); yz++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(yz).value();
		}

	//	System.out.println("PlayerHand total= " + playerHand);

//		System.out.println("dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

	//	System.out.println("playerHand count = " + playerHand);

//		int dealerHas = dealerCards.get(1).value();

	//	System.out.println("dealer has  = " + dealerHas);		

////////////////////////////////////////////////////////////////////////////////
/////////////////// adjust hitting rules here ////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

		////////////
		// need to know if should turn Ace into 11 or 1 here....
		////////////

		if(dealerHand<17){
			shouldHitIt = true;
		}
		else{
			shouldHitIt = false;
		}

//		if(dealerHas<6 && playerHand>12){
//			shouldHitIt = false;
//		}

//		if(dealerHas>6 && playerHand<16){
//			shouldHitIt = true;
//		}

		return shouldHitIt;

}

	public Boolean busted(RandIndexQueue<Card> playerCards){
		boolean isBusted = false;

		int playerHand=0;
		int aces = 0;


		for (int y=0; y<playerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(y).value();

			if(playerCards.get(y).value()==11){
				aces++;

			}
		}


		while(playerHand>21 && aces>0){
			// use ace as one instead of 11... not subtract 10....
			playerHand-=10;
			aces--;
		}

		if(playerHand>21){
			isBusted = true;
		}
		return isBusted;

	}

public int cardValue(RandIndexQueue<Card> playerCards){
	int playerHand = 0;
	int aces = 0;


	for (int y=0; y<playerCards.size(); y++){
	//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
		playerHand+=playerCards.get(y).value();

		if(playerCards.get(y).value()==11){
			aces++;

		}
	}

	while(playerHand>21 && aces>0){
			// use ace as one instead of 11... not subtract 10....
			playerHand-=10;
			aces--;
		}

	return playerHand;


}

/*	public Boolean hasAce(RandIndexQueue<Card> playerCards){

		boolean hasAnAce =false;

		//int aces = 0;
		for (int aaa=0; aaa<playerCards.size();  aaa++){
			//int tmp = playerCards.get(aaa).value();

			if(playerCards.get(aaa).value()==11){
				hasAnAce = true;
			}

		}
		return hasAnAce;

	} */

}



// to do 

// account for ace - if total is greater than 21 but last card was an ace, change to 1 instead of 11

// trace version vs non trace?
// check if number of rounds argument is <= 10 first, if more, then run headless version
// copy and paste everything, don't do system prints except when reshuffling


// todo automate

// automate original bank pass in as 3rd argument

// betting strategy, counting cards, if deck shoe gets loaded

