public class Game2{

	int playerWins, playerBUSTED;
	int dealerWins, dealerBUSTED;
	int pushes;
	int rounds;
	int playerBlackJack;
	int dealerBlackJack;
	int playerBank;

	int originalBank;

	int reUP = 0;
	int betAmount=0;
	int bigBetAmount =0;

	int bigBetWins = 0;
	int atmLoss = 0;

	double totalDecks;
	int totalCards = 0;

	Boolean headless = false;

	RandIndexQueue<Card> discard = new RandIndexQueue<>(2);


	double theCount = 0; // counting cards 

	public Game2(int rounds, int decks, int playerBank, Boolean headless, int betAmount){
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

		
		this.headless = headless;
		this.betAmount = betAmount;

		bigBetAmount = 20*betAmount;

		totalDecks = (double) decks;
		totalCards = decks*52;
	}

	public void playGame(RandIndexQueue<Card> theShoe){

	//	System.out.println("HEADLESS?? = " + headless);
	//	System.out.println("you made it to playGame");
		// play as many rounds as passed from original argument
		originalBank = playerBank;

		for (int i=0; i<rounds; i++){
			if(!headless){
				System.out.println("########## Start of Round " + i +  " ##########");
			}

			// if theSHoe is less than 25% clear, push all discard back into theSHoe and shuffle
			if(((double) theShoe.size() / (double) totalCards) < .25){
				// put dicard back in shoe
				// shuffle

				//System.out.println("should only be less than .25" + ((double) theShoe.size()/ (double) totalCards));

				System.out.println("| RESHUFFLING THE SHOE IN ROUND " + i + " |");

				for (int ii=0; ii<discard.size(); ii++){
					theShoe.offer(discard.poll());

						theShoe.shuffle();
						theShoe.shuffle();
						theShoe.shuffle();
				}
			}
			// play a round of cards
			if(playerBank<0){
				System.out.println("Player Lost all money :(");
				System.out.println("Current bank amount is $"+ playerBank);

				// stays negative so add it back to playerBank at end 
				atmLoss += playerBank * -1;

				System.out.println("ATM LOSSES = " + atmLoss);

				System.out.println("PLAYER GOES TO ATM AND GETS $ " + originalBank);
				//playerBank= playerBank + originalBank;
				System.out.println("NOW, Current bank amount is $"+  (playerBank+=originalBank));
				reUP++;
			}
			playRound(theShoe);
			//int tmp = theShoe.size();
			//System.out.println("theShoe size = " + tmp);
		}



		System.out.println("###############################################");
		System.out.println("############## BLACKJACK RESULTS ##############");
		System.out.println("###############################################");


		System.out.println("REUPs = " + reUP);
		System.out.println("PUSHES = " + pushes);

		System.out.println("Player BlackJack = " + playerBlackJack);
		System.out.println("Dealer BlackJack = " + dealerBlackJack);

		System.out.println("Player Wins = " + playerWins);
		System.out.println("Dealer Wins = " + dealerWins);

		System.out.println("Player Busts = " + playerBUSTED);
		System.out.println("Dealer Busts = " + dealerBUSTED);

		System.out.println("Big Bet Win/Loss (+/-) = " + bigBetWins);

		System.out.println("Player Final Bank = $" + (playerBank-=atmLoss));
// print whast in discard
		//for (int y=0; y<discard.size(); y++){
		//	System.out.println("discard cards = " + discard.get(y) + " " +discard.get(y).value());
			//playerHand+=playerCards.get(y).value();

		//	}

	}

	public void playRound(RandIndexQueue<Card> theShoe){

		System.out.println("Player CURRENT Bank = $" + playerBank);
		
		if(!headless){
			System.out.println("Player CURRENT Bank = $" + playerBank);	
		}
		//System.out.println("Player CURRENT Bank = $" + playerBank);


/////// every round should start this way

		RandIndexQueue<Card> dealerCards = new RandIndexQueue<Card>(1);
		RandIndexQueue<Card> playerCards = new RandIndexQueue<Card>(1);

		boolean bigBet =false;

		
		if(theCount>10){
				//betAmount = bigBetAmount
				if(!headless){
					System.out.println("PLAYER BETS HUUUUGE $" + bigBetAmount);
				}
				

				bigBet = true;
			}
			else{
				if(!headless){
					System.out.println("PLAYER BETS $" + betAmount);
				}
			}
		

		


		int playerNUMCARDS = 0;
		int dealerNUMCARDS = 0;

///////////////////////////////////// deal initial cards /////////////////////////////////////////////////////////

		playerCards.offer(theShoe.poll());
		playerNUMCARDS++;

		dealerCards.offer(theShoe.poll());

		playerCards.offer(theShoe.poll());
		playerNUMCARDS++;

		dealerCards.offer(theShoe.poll());	


		// i realized after i wrote the cardValue method i could have replaced the below code but oh well
		int dealerHandStart = 0;
		int playerHandStart = 0;


		//System.out.println("dealerCURRENTWins= " + dealerWins);
		//System.out.println("playererCURRENTWins= " + playerWins);

		for (int yzzzy=0; yzzzy<dealerCards.size(); yzzzy++){
		//	System.out.println("dealers's cards = " + dealerCards.get(yzzzy) + " " +dealerCards.get(yzzzy).value());
			dealerHandStart+=dealerCards.get(yzzzy).value();

			}

		for (int yzzz=0; yzzz<playerCards.size(); yzzz++){
		//	System.out.println("players's cards = " + playerCards.get(yzzz) + " " +playerCards.get(yzzz).value());
			playerHandStart+=playerCards.get(yzzz).value();

			}


	int playerTotal = cardValue(playerCards);
	int dealerTotal = cardValue(dealerCards);

//////////////////////////////////// anyone get blackjack? /////////////////////////////////////////////////////

		if(dealerHandStart==21 && playerHandStart==21){
			pushes++;

			if(!headless){
				System.out.println("Dealer: " + dealerCards.toString() + " : " + dealerTotal);
				System.out.println("Player: " + playerCards.toString() + " : " + playerTotal);
				System.out.println("Result: Push!");
			}			
			//System.out.println("Dealer: " + dealerCards.toString() + " : " + dealerTotal);
			//System.out.println("Player: " + playerCards.toString() + " : " + playerTotal);
			//System.out.println("Result: Push!");
			//System.out.println("PUSH: Both have blackjack");

			updateCount(playerCards);
			updateCount(dealerCards);

			if(!headless){
				System.out.println("THE COUNT IS: " + theCount);
			}

			for (int xx=0; xx<playerCards.size(); xx++){
			discard.offer(playerCards.get(xx));

			}
			for (int yy=0; yy<dealerCards.size(); yy++){
			discard.offer(dealerCards.get(yy));

			}

			if(!headless){
				System.out.println("\n");
			}

			return;
		}	

		if(playerHandStart==21 && dealerHandStart<21){
			//System.out.println("___PLAYERBLACKJACK___");

			if(!bigBet){
				playerBank += betAmount*1.5;	
			}
			if(bigBet){
				playerBank += bigBetAmount*1.5;
				bigBetWins++;
				//System.out.println("============== BIG BET WIN! =============");
			}

			if(!headless){
				System.out.println("Dealer: " + dealerCards.toString() + " : " + dealerTotal);
				System.out.println("Player: " + playerCards.toString() + " : " + playerTotal);
				System.out.println("Results: Player wins with BlackJack!");
			}
			//System.out.println("---------------------PlayerWins!");

	

			playerWins++;
			playerBlackJack++;
			
			updateCount(playerCards);
			updateCount(dealerCards);

			if(!headless){
				System.out.println("THE COUNT IS: " + theCount);
			}

			for (int xxx=0; xxx<playerCards.size(); xxx++){
			discard.offer(playerCards.get(xxx));

			}
			for (int yyy=0; yyy<dealerCards.size(); yyy++){
			discard.offer(dealerCards.get(yyy));

			}

			if(!headless){
				System.out.println("\n");
			}

			return;
		}

		if(dealerHandStart==21 && playerHandStart<21){
			//System.out.println("___DEALERBLACKJACK___");
			if(!bigBet){
				playerBank -= betAmount;	
			}
			
			if(bigBet){
				playerBank -= bigBetAmount;
				bigBetWins--;;
				//System.out.println("============== BIG BET LOSS! =============");
			}

			if(!headless){
				System.out.println("Dealer: " + dealerCards.toString() + " : " + dealerTotal);
				System.out.println("Player: " + playerCards.toString() + " : " + playerTotal);
				System.out.println("Results: Dealer wins with BlackJack!");
			}
			//System.out.println("---------------------DealerWins!");



			updateCount(playerCards);
			updateCount(dealerCards);

			if(!headless){
				System.out.println("THE COUNT IS: " + theCount);
			}

			dealerWins++;
			dealerBlackJack++;
			for (int xxyy=0; xxyy<playerCards.size(); xxyy++){
			discard.offer(playerCards.get(xxyy));

			}
			for (int yyxx=0; yyxx<dealerCards.size(); yyxx++){
			discard.offer(dealerCards.get(yyxx));

			}

			if(!headless){
				System.out.println("\n");
			}
			return;
		}

		
/////////////////////////////////////////////// hit or stay? ///////////////////////////////////////////////////////

		


//		int playerTotal = cardValue(playerCards);

		boolean playerBusted = false;
		boolean dealerBusted = false;

		boolean nextCard = true;

		//System.out.println("Player Hand = " + playerTotal);	


		//int dealerHas = dealerCards.get(1).value();
		//System.out.println("Dealer: UPCARD : " + dealerCards.get(1) + " " +dealerCards.get(1).value());

		if(!headless){
			System.out.println("Dealer: UPCARD : " + dealerCards.get(1) + " : " +dealerCards.get(1).value());
			System.out.println("Player: " + playerCards.toString() + " : " + playerTotal);
		}

		while(nextCard && playerShouldHit(dealerCards, playerCards)){
		//while(nextCard && (dealerHas<6 && playerTotal>=12)){
			//System.out.println("**** PLAYER Hitting****");
			playerCards.offer(theShoe.poll());
			if(!headless){
				System.out.println("Player: HITS: " + playerCards.get(playerNUMCARDS));
			}
			playerNUMCARDS++;
			//System.out.println("NUmber of player cards = " + playerNUMCARDS);
			playerTotal = cardValue(playerCards);
		//	System.out.println("Player Hand = " + playerTotal);	
			if(playerTotal>21){
				//System.out.println("###PLAYER BUSTED###");
				if(!headless){
					System.out.println("Player: BUSTS: " + playerCards.toString());
				}
				playerBusted = true;
				playerBUSTED++;
				dealerWins++;
				nextCard = false;
			}
			nextCard = true;

		}

		//System.out.println("****PLAYER Staying****");	
		if(!playerBusted){
			if(!headless){
				System.out.println("Player: STANDS: " + playerCards.toString() + " : " + playerTotal );
			}		
		}
		//System.out.println("Player: STANDS: " + playerCards.toString() + " : " + playerTotal );	

// if player busted dont enter loop to hit for dealer!


//		int dealerTotal = cardValue(dealerCards);

		if(!headless){
			System.out.println("Dealer: " + dealerCards.toString() + " : " + dealerTotal);
		}

		nextCard = true;
		while(!playerBusted && nextCard && dealerShouldHit(dealerCards, playerCards)){
		//while(nextCard && dealerTotal<17){
			//System.out.println("****DEALER Hitting****");
			if(!headless){
				System.out.println("Dealer: HITS: " + dealerCards.get(dealerNUMCARDS));
			}
			dealerNUMCARDS++;
			dealerCards.offer(theShoe.poll());
			dealerTotal = cardValue(dealerCards);
			if(dealerTotal>21){
				//System.out.println("###DEALER BUSTED###");
				if(!headless){
					System.out.println("Dealer: BUSTS: " + dealerCards.toString());
				}
				dealerBusted = true;
				dealerBUSTED++;
				playerWins++;
				nextCard=false;
			}
			nextCard = true;
			
		}

		//System.out.println("****DEALER Staying****");	
		if(!dealerBusted){
			if(!headless){
				System.out.println("Dealer: STANDS: " + dealerCards.toString() + " : " + dealerTotal );	
			}	
		}
		//System.out.println("Dealer: STANDS: " + playerCards.toString() + " : " + dealerTotal );	



		//System.out.println("Player FINAL Hand = " + playerTotal);	
		//System.out.println("Dealer FINAL Hand = " + dealerTotal);	


	//System.out.println("PLAYER: " + playerCards.toString() + " : " + playerTotal);			
	//System.out.println("DEALER: " + dealerCards.toString() + " : " +  dealerTotal);

/////////////////////////////////////////////////////////////////////////////////////////////
/// determining who wont he round
/////////////////////////////////////////////////////////////////////////////////////////////

		if ((playerTotal <= 21) && (dealerTotal < playerTotal) || dealerBusted){
			playerWins++;
			//System.out.println("---------------------PlayerWins!");
			if(!headless){
				System.out.println("Result: Player Wins!");

				if(!bigBet){
					playerBank += betAmount;	
				}

				if(bigBet){
					playerBank += bigBetAmount;
					bigBetWins++;
					//System.out.println("============== BIG BET WIN! =============");
				}
			}
		}

		if ((dealerTotal <= 21 ) && (playerTotal < dealerTotal || playerBusted)){
			dealerWins++;
			//System.out.println("---------------------DealerWins!");
			if(!headless){
				System.out.println("Result: Dealer Wins!");
				
				if(!bigBet){
					playerBank -= betAmount;	
				}
				else{
					playerBank -= bigBetAmount;
					bigBetWins--;
					//System.out.println("============== BIG BET LOSS! =============");
				}
			}
		}

		if (playerTotal == dealerTotal){
			pushes++;
			//System.out.println("---------------------Push!");
			if(!headless){
				System.out.println("Result: Push!");
			}

		}



/*		if(((playerTotal > dealerTotal) && playerTotal <21 && !playerBusted) || dealerBusted){
			playerWins++;
			System.out.println("PlayerWins!");
		}
		else if (playerTotal == dealerTotal){
			pushes++;
			System.out.println("Push!");
		}
		else if (((dealerTotal > playerTotal) && dealerTotal < 21 && !dealerBusted) || playerBusted){
			dealerWins++;
			System.out.println("DealerWins!!");
		} */
//		else{
//			dealerWins++;
//			System.out.println("DealerWins!");
//		}
		


/*		for (int y=0; y<dealerCards.size(); y++){
			System.out.println("dealers cards = " + dealerCards.get(y));
		}

		for (int yz=0; yz<playerCards.size(); yz++){
			System.out.println("players's cards = " + playerCards.get(yz));
		}

*/

		//System.out.println("***dealer's UPCARD = " + dealerCards.get(1) + " " +dealerCards.get(1).value());

		updateCount(playerCards);
		updateCount(dealerCards);

		if(!headless){
			System.out.println("THE COUNT IS: " + theCount);
		}

		for (int xx=0; xx<playerCards.size(); xx++){
			discard.offer(playerCards.get(xx));

			}
		for (int yy=0; yy<dealerCards.size(); yy++){
			discard.offer(dealerCards.get(yy));

			}

		if(!headless){
			System.out.println("\n");
		}

	}

	public Boolean playerShouldHit(RandIndexQueue<Card> dealerCards, RandIndexQueue<Card> playerCards){

		boolean shouldHitIt = true;

		int playerHand=0;

		//for (int y=0; y<playerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
		//	playerHand+=playerCards.get(y).value();
		//}


		playerHand = cardValue(playerCards);


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


		if(dealerHas<6 && playerHand>=12){
			shouldHitIt = false;
		}

		if(dealerHas>6 && playerHand<16){
			shouldHitIt = true;
		}

		if(playerHand>16){
			shouldHitIt = false;
		}

		

		return shouldHitIt;

}


	public Boolean dealerShouldHit(RandIndexQueue<Card> dealerCards, RandIndexQueue<Card> playerCards){

		boolean shouldHitIt = false;

		int dealerHand=0;

		//for (int y=0; y<dealerCards.size(); y++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
		//	dealerHand+=dealerCards.get(y).value();
		//}

		dealerHand = cardValue(dealerCards);


//		System.out.println("CURRENT DEALER HAND = " + dealerHand);


		int playerHand=0;

		//for (int yz=0; yz<playerCards.size(); yz++){
		//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());
	//		playerHand+=playerCards.get(yz).value();
	//	}

		playerHand = cardValue(playerCards);


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

public void updateCount(RandIndexQueue<Card> playerCards){
	for (int y=0; y<playerCards.size(); y++){
	//	System.out.println("players's cards = " + playerCards.get(y) + " " +playerCards.get(y).value());

		if(playerCards.get(y).value() == 1 || playerCards.get(y).value() == 11 || playerCards.get(y).value() == 10){
			theCount--;

		}

		if(playerCards.get(y).value() == 2 || playerCards.get(y).value() == 3 || playerCards.get(y).value() == 4 || playerCards.get(y).value() == 5 || playerCards.get(y).value() == 6){
			theCount++;

		}
	}
}


}


/// questions = should i display shuffling if it happens in the first 10 rounds?
// done


// to do 

// account for ace - if total is greater than 21 but last card was an ace, change to 1 instead of 11
// done

// trace version vs non trace?
// check if number of rounds argument is <= 10 first, if more, then run headless version
// copy and paste everything, don't do system prints except when reshuffling


// todo automate

// automate original bank pass in as 3rd argument
// done

// betting strategy, counting cards, if deck shoe gets loaded

