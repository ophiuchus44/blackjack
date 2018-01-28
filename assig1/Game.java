public class Game{


	int playerWins, playerBUSTED;
	int dealerWins, dealerBUSTED;
	int pushes;
	int rounds;
	int playerBank;

	RandIndexQueue<Card> discard = new RandIndexQueue<>(2);

	public Game(int rounds, int playerBank){
		// initialize to zero and get rounds
		playerWins= 0;
		dealerWins= 0;
		pushes= 0;

		playerBUSTED = 0;
		dealerBUSTED = 0;
		this.rounds = rounds;
		this.playerBank = playerBank;
	}

	public void playGame(RandIndexQueue<Card> theShoe){
		System.out.println("you made it to playGame");
		// play as many rounds as passed from original argument
		

		for (int i=0; i<rounds; i++){
			System.out.println("########## Start of Round " + i +  "##########");

			// if theSHoe is less than 25% clear, push all discard back into theSHoe and shuffle
			playRound(theShoe);
			int tmp = theShoe.size();
			System.out.println("theShoe size = " + tmp);
		}



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

		for (int y=0; y<playerCards.size(); y++){
			System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(y).value();

			}
		System.out.println("****Staying****");	
		System.out.println("PlayerHand = " + playerHand);	

		int dealerHand = 0;

		for (int yyy=0; yyy<dealerCards.size(); yyy++){
			System.out.println("dealer's cards = " + dealerCards.get(yyy) + " " +dealerCards.get(yyy).value());
			dealerHand+=dealerCards.get(yyy).value();
			}


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



		if(((playerHand > dealerHand) && !playerBusted) || dealerBusted){
			playerWins++;
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

		System.out.println("dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

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

		if(dealerHas<6 && playerHand>12){
			shouldHitIt = false;
		}

		if(dealerHas>6 && playerHand<16){
			shouldHitIt = true;
		}

		return shouldHitIt;

}


	public Boolean dealerShouldHit(RandIndexQueue<Card> dealerCards, RandIndexQueue<Card> playerCards){

		boolean shouldHitIt = true;

		int dealerHand=0;

		for (int y=0; y<dealerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			dealerHand+=dealerCards.get(y).value();
		}



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

		for (int y=0; y<playerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
			playerHand+=playerCards.get(y).value();
		}

		if(playerHand>21){
			isBusted = true;
		}
		return isBusted;

	}

}



// to do 

// trace version vs non trace?
// check if number of rounds argument is <= 10 first, if more, then run headless version
// copy and paste everything, don't do system prints except when reshuffling


// todo automate

// automate original bank pass in as 3rd argument

// betting strategy, counting cards, if deck shoe gets loaded

