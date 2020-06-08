package com.kata.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PokerService extends PokerServiceHelper{

	
	public String processHands(String white, String black) {
		
		Map<Integer,String> whiteResultMap = getHandResult(white);
		Map<Integer,String> blackResultMap = getHandResult(black);

		int whiteRank=0;
		String whiteResult = "";
		for (Map.Entry<Integer,String> entry : whiteResultMap.entrySet()) {
			whiteRank = entry.getKey();
			whiteResult=entry.getValue();
		}
		int blackRank=0;
		String blackResult = "";
		for (Map.Entry<Integer,String> entry : blackResultMap.entrySet()) {
			blackRank = entry.getKey();
			blackResult=entry.getValue();
		}
		if(whiteRank==blackRank) {
			return processEqualHandResults(whiteRank,whiteResult,white,black);
		}
		return whiteRank < blackRank
				? "White Wins with " + whiteResult
				: "Black Wins with " + blackResult;

	}
	
	protected String processEqualHandResults(int rank, String result, String white, String black) {		

		switch(rank) {
		  case 1:
			  return "Tie, Share the POT!!";
		  case 2://straight flush
			  return getHighCard(white)>getHighCard(black)?"White Wins with " + result
						: getHighCard(white)<getHighCard(black)?"Black Wins with " + result:"Tie, Share the POT!!";
		  case 3://four of a kind
			return processDuplicateHands(result,rank,white,black);
			 case 4://full house
				 
			return processDuplicateHands(result,rank,white,black);
		  case 5://flush
			  return  getSumOfHands(white)>getSumOfHands(black)?"White Wins with " + result:
				  getSumOfHands(white)<getSumOfHands(black)? "Black Wins with " + result:"Tie, Share the POT!!";
		  case 6://straight
			  return  getHighCard(white)>getHighCard(black)?"White Wins with " + result
						: getHighCard(white)<getHighCard(black)?"Black Wins with " + result:"Tie, Share the POT!!";
		  case 7://three of a kind
	          return processDuplicateHands(result,rank,white,black);
		  case 8://two pair
			  return processDuplicateHands(result,rank,white,black);
		  case 9://pair
			  return processDuplicateHands(result,rank,white,black);
		  case 10:// High Card
			  return Integer.valueOf(getHighCard(white))>Integer.valueOf(getHighCard(white))?"White Wins with " + result
						: "Black Wins with " + result;
		}
		
		return "Tie, Share the POT!!";
	}

	protected String processDuplicateHands(String result, int rank,String wHand, String bHand) {
		int dupCrdValOfWhte=0;
		int dupCrdValOfBlk=0;
		int remCrdValOfWhte=0;
		int remCrdValOfBlk=0;
		List<Integer> wList = sortHand(wHand);
		Map<Integer, Integer> wMap = getCardCounts(wList);
		List<Integer> bList = sortHand(bHand);
		Map<Integer, Integer> bMap = getCardCounts(bList);
		if(rank==3) {
			dupCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 4)).map(Map.Entry::getKey).findFirst()
					.get();
			 dupCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 4)).map(Map.Entry::getKey).findFirst()
					.get();
			 remCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 1)).map(Map.Entry::getKey).findFirst()
					.get();
			 remCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 1)).map(Map.Entry::getKey).findFirst()
					.get();
		}else if(rank==4) {
			 dupCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 3)).map(Map.Entry::getKey).findFirst()
					.get();
			 dupCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 3)).map(Map.Entry::getKey).findFirst()
					.get();
			 remCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 2)).map(Map.Entry::getKey).findFirst()
					.get();
			 remCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 2)).map(Map.Entry::getKey).findFirst()
					.get();
		}else if(rank==7) {
			 dupCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 3)).map(Map.Entry::getKey).findFirst()
					.get();
			 dupCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 3)).map(Map.Entry::getKey).findFirst()
					.get();
			 List<Integer> wl = new ArrayList<>();
			 wl.addAll(wMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 1))
						.map(Map.Entry::getKey).collect(Collectors.toSet()));
			 Collections.sort(wl);
			
			 List<Integer> bl = new ArrayList<>();
			 bl.addAll(bMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 1))
						.map(Map.Entry::getKey).collect(Collectors.toSet()));
			 Collections.sort(bl);
			 int whiteKicker = wl.get(1);
			 int whiteNextKicker =  wl.get(0);
			 int blackKicker = bl.get(1);
			 int blackNextKicker =  bl.get(0);
				return dupCrdValOfWhte > dupCrdValOfBlk ? "White Wins with " + result
						: dupCrdValOfWhte < dupCrdValOfBlk ? "Black Wins with " + result
								: (dupCrdValOfWhte == dupCrdValOfBlk) && (whiteKicker > blackKicker)
										? "White wins with " + result
										: (dupCrdValOfWhte == dupCrdValOfBlk) && (whiteKicker < blackKicker)
												? "Black wins with " + result
												: ((dupCrdValOfWhte == dupCrdValOfBlk) && (whiteKicker == blackKicker))
														&& (whiteNextKicker > blackNextKicker)
																? "White wins with " + result
																: ((dupCrdValOfWhte == dupCrdValOfBlk)
																		&& (whiteKicker == blackKicker))
																		&& (whiteNextKicker < blackNextKicker)
																				? "Black wins with " + result
																				: "Tie, Share the POT!!";
			 
		}
		else if(rank==8) {
				 List<Integer> wl = new ArrayList<>();
				 wl.addAll(wMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 2))
							.map(Map.Entry::getKey).collect(Collectors.toSet()));
				 Collections.sort(wl);
				
				 List<Integer> bl = new ArrayList<>();
				 bl.addAll(bMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 2))
							.map(Map.Entry::getKey).collect(Collectors.toSet()));
				 Collections.sort(bl);
				 int whiteFirstDup = wl.get(1);
				 int whiteNextDup =  wl.get(0);
				 int blackFirstDup = bl.get(1);
				 int blackNextDup =  bl.get(0);
				 int whiteKicker = wMap.entrySet().stream().filter(entry -> (entry.getValue() == 1)).map(Map.Entry::getKey).findFirst()
							.get();
				 int blackKicker = bMap.entrySet().stream().filter(entry -> (entry.getValue() == 1)).map(Map.Entry::getKey).findFirst()
							.get();
				 
			
					return whiteFirstDup > blackFirstDup ? "White Wins with " + result
							: whiteFirstDup < blackFirstDup ? "Black Wins with " + result
									: (blackFirstDup == whiteFirstDup) && (whiteNextDup > blackNextDup)
											? "White wins with " + result
											: (blackFirstDup == whiteFirstDup) && (whiteNextDup < blackNextDup)
													? "Black wins with " + result
													: (blackFirstDup == whiteFirstDup) && (whiteNextDup == blackNextDup)
															&& (whiteKicker > blackKicker)
																	? "White wins with " + result
																	: (blackFirstDup == whiteFirstDup)
																			&& (whiteNextDup == blackNextDup)
																			&& (whiteKicker < blackKicker)
																					? "Black wins with " + result
																					: "Tie, Share the POT!!";
		    
		    
		}
		else if(rank==9) {
			 dupCrdValOfWhte=wMap.entrySet().stream().filter(entry -> (entry.getValue() == 2)).map(Map.Entry::getKey).findFirst()
						.get();
				 dupCrdValOfBlk=bMap.entrySet().stream().filter(entry -> (entry.getValue() == 2)).map(Map.Entry::getKey).findFirst()
						.get();
				 List<Integer> wl = new ArrayList<>();
				 wl.addAll(wMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 1))
							.map(Map.Entry::getKey).collect(Collectors.toSet()));
				 Collections.sort(wl);
				
				 List<Integer> bl = new ArrayList<>();
				 bl.addAll(bMap.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 1))
							.map(Map.Entry::getKey).collect(Collectors.toSet()));
				 Collections.sort(bl);
				 int whiteKicker = wl.get(2);
				 int whiteNextKicker =  wl.get(1);
				 int whiteLastKicker = wl.get(0);
				 int blackKicker = bl.get(2);
				 int blackNextKicker =  bl.get(1);
				 int blackLastKicker = bl.get(0);
				 
					return dupCrdValOfWhte > dupCrdValOfBlk ? "White Wins with " + result
							: dupCrdValOfWhte < dupCrdValOfBlk ? "Black Wins with " + result
									: (dupCrdValOfWhte == dupCrdValOfBlk) && (whiteKicker > blackKicker)
											? "White wins with " + result
											: (dupCrdValOfWhte == dupCrdValOfBlk) && (whiteKicker < blackKicker)
													? "Black wins with " + result
													: ((dupCrdValOfWhte == dupCrdValOfBlk)
															&& (whiteKicker == blackKicker))
															&& (whiteNextKicker > blackNextKicker)
																	? "White wins with " + result
																	: ((dupCrdValOfWhte == dupCrdValOfBlk)
																			&& (whiteKicker == blackKicker))
																			&& (whiteNextKicker < blackNextKicker)
																					? "Black wins with " + result
																					: ((whiteNextKicker == blackNextKicker)
																							&& (whiteLastKicker > blackLastKicker))
																									? "White wins with "
																											+ result
																									: ((whiteNextKicker == blackNextKicker)
																											&& (whiteLastKicker > blackLastKicker))
																													? "Black wins with "
																															+ result
																													: "Tie, Share the POT!!";

		}
		
		return dupCrdValOfWhte > dupCrdValOfBlk ? "White Wins with " + result
				: dupCrdValOfWhte < dupCrdValOfBlk ? "Black Wins with " + result
						: (dupCrdValOfWhte == dupCrdValOfBlk) && (remCrdValOfWhte > remCrdValOfBlk)
								? "White wins with " + result
								: (dupCrdValOfWhte == dupCrdValOfBlk) && (remCrdValOfWhte < remCrdValOfBlk)
										? "Black wins with " + result
										: "Tie, Share the POT!!";
	}
	
	
		
}
