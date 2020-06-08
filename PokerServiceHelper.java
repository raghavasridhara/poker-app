/**
 * 
 */
package com.kata.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Raghavender Sridhara
 *
 */
public class PokerServiceHelper {
	
	
	protected int getSumOfHands(String hand) {
		return sortHand(hand).stream().mapToInt(Integer::intValue).sum();
	}

	public Map<Integer,String> getHandResult(String hand) {
		Map<Integer,String> resultMap = new HashMap<>();
		if (isRoyalFlush(hand)) {
			resultMap.put(1,"Royal Flush");
			return resultMap;
		} else if (isStraightFlush(hand)) {
			resultMap.put(2,"Straight Flush");
			return resultMap;
		} else if (isFourOfaKind(hand)) {
			resultMap.put(3,"Four of A Kind");
			return resultMap;
		} else if (isFullHouse(hand)) {
			resultMap.put(4,"Full House");
			return resultMap;
		} else if (isFlush(hand)) {
			resultMap.put(5,"Flush");
			return resultMap;
		} else if (isStraight(hand)) {
			resultMap.put(6,"Straight");
			return resultMap;
		} else if(isThreeOfaKind(hand)){
			resultMap.put(7,"Three Of A Kind");
			return resultMap;
		}else if(isTwoPair(hand)){
			resultMap.put(8,"Two Pair");
			return resultMap;
		}else if (isPair(hand)) {
			resultMap.put(9,"A Pair");
			return resultMap;
		}else {
			resultMap.put(10,"High Card");
			return resultMap;
		}

	}
	
	protected Boolean isFourOfaKind(String hand) {
		List<Integer> list = sortHand(hand);
		SortedSet<Integer> rankSet = new TreeSet<>();
		rankSet.addAll(list);
		Map<Integer, Integer> map = getCardCounts(list);
		return rankSet.size() == 2 && ((map.get(rankSet.first()) == 4 && map.get(rankSet.last()) == 1)
				|| (map.get(rankSet.last()) == 4 && map.get(rankSet.first()) == 1)) ? true : false;
	}
	
	protected Boolean isTwoPair(String hand) {
		List<Integer> list = sortHand(hand);
		SortedSet<Integer> rankSet = new TreeSet<>();
		rankSet.addAll(list);
		Map<Integer, Integer> map = getCardCounts(list);		
		return rankSet.size()==3&&(map.get(rankSet.first())==2 || map.get(rankSet.last())==2)?true:false;
	}
	
	protected Boolean isThreeOfaKind(String hand) {
		List<Integer> list = sortHand(hand);
		SortedSet<Integer> rankSet = new TreeSet<>();
		rankSet.addAll(list);
		Map<Integer, Integer> map = getCardCounts(list);		
		return rankSet.size()==3&&(map.get(rankSet.first())==3 || map.get(rankSet.last())==3)?true:false;
	}
	
	protected Boolean isFullHouse(String hand) {
		List<Integer> list = sortHand(hand);
		SortedSet<Integer> rankSet = new TreeSet<>();
		rankSet.addAll(list);
		Map<Integer, Integer> map = getCardCounts(list);
		return rankSet.size() == 2 && ((map.get(rankSet.first()) == 3 && map.get(rankSet.last()) == 2)
				|| (map.get(rankSet.first()) == 2 && map.get(rankSet.last()) == 3)) ? true : false;
	}


	protected Map<Integer, Integer> getCardCounts(List<Integer> list) {
		Map<Integer,Integer> map = new HashMap<>();
		int j=1;
		for(int i=0;i<list.size();i++) {
			
			if(map.containsKey(list.get(i))) {
				map.put(list.get(i), ++j);
			}else {
				j=1;
				map.put(list.get(i), j);
			}
		}
		return map;
	}
	
	protected int getHighCard(String hand) {
		return sortHand(hand).get(sortHand(hand).size()-1);
	}
	
	protected Boolean isPair(String hand) {
		Set<Integer> rankSet = new HashSet<>();
		rankSet.addAll(sortHand(hand));
		return rankSet.size()==4?true:false;
	}
	
	
	
	
	public boolean isRoyalFlush(String hand) {
		return isFlush(hand) && hand.contains("10") && hand.contains("J") && hand.contains("Q") && hand.contains("K")
				&& hand.contains("A") ? true : false;
	}
	
	public boolean isStraight(String hand) {
		List<Integer> list = sortHand(hand);
		for(int i=0;i<list.size()-1;i++) {
			if((list.get(i)-list.get(i+1))==-1) {
			continue;	
			}else {
				return false;
			}
		}
		return true;
	}
	
	public boolean isStraightFlush(String hand) {
		return isFlush(hand)&&isStraight(hand);
	}

	public Boolean isFlush(String hand) {
		Set<String> suitSet = iterateOverSuitsOfHand(hand);		
		return suitSet.size()>1?false:true;
	}

	protected Set<String> iterateOverSuitsOfHand(String hand) {
		Set<String> suitSet = new HashSet<>();
		String[] cards = hand.split(",");
		for(String cd: cards) {
			suitSet.add(cd.substring(cd.length()-1));
		}
		return suitSet;
	}
	
	public List<Integer> sortHand(String hand) {
		List<Integer> cardList = new ArrayList<>();
		String[] cards = hand.split(",");
		for(String cd: cards) {
			cardList.add(replaceFaceCardsWithNumbers(cd.substring(0,cd.length()-1)));	
		}	
		Collections.sort(cardList);
		return cardList ;
	}
	
	protected int replaceFaceCardsWithNumbers(String inputCard) {
		 if(inputCard.equals("J")) {
			 return 11;
		 }else if(inputCard.equals("Q")) {
			 return 12;
		 }else if(inputCard.equals("K")) {
			 return 13;
		 }else if(inputCard.equals("A")) {
			 return 14;
		 }
		 return Integer.valueOf(inputCard);
		
	}

}
