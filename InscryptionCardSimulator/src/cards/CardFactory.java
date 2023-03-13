package cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import effects.Effect;

public class CardFactory {
	public static String beastAppearances[] = {"kingfisher", "raven_egg", "sparrow", "magpie", "raven", "turkey_vulture", "stunted_wolf","wolf_cub"};
	
	public static String robotAppearances[] = {"s0n1a","qu177","gr1zz","bomb_latcher","exeskeleton","shield_latcher"};
	
	public static BeastCard beastCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int u = u0;
		int nbstats = 0;
		int levelRarity = 0;
		int level = 0;
		int attackmin = 0;
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		String typeCost = "blood";
		if (u%4 == 3) {
			typeCost = "bone";
			u = (u * multiplicator + 2*levelRarity)%modulo;
			int lvlmax = 8;
			ArrayList<Integer> integerSeen = new ArrayList<Integer>();
			while (u%4 == 3 && !integerSeen.contains(u)) {
				integerSeen.add(u);
				u = (u * multiplicator + 2*levelRarity)%modulo;
				lvlmax++;
			}
			level = 2 + u%(lvlmax-1);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = level * globalStrengh / 4 + levelRarity * rarityStrengh;
			nbstats = nbstats - u%(1+globalStrengh/8);
			u = (u * multiplicator + 2*levelRarity)%modulo;
		} else {
			int lvlmax = 2;
			u = (u * multiplicator + 2*levelRarity)%modulo;
			ArrayList<Integer> integerSeen = new ArrayList<Integer>();
			while (u%4 == 3 && !integerSeen.contains(u)) {
				integerSeen.add(u);
				u = (u * multiplicator + 2*levelRarity)%modulo;
				lvlmax++;
			}
			level = 1 + u%(lvlmax);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = level * globalStrengh + levelRarity * rarityStrengh;
			nbstats = nbstats - u%(1+globalStrengh/4);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = nbstats + Math.max(0, level - 1 - u%(1+globalStrengh/4));
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%4>0) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName)) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		//effect2
		if (!stopEffects) {
		if (u%4>1) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect3
		if (!stopEffects) {
		if (u%4>2) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
				u = (u * multiplicator + 2*levelRarity)%modulo;
				levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
				u = (u * multiplicator + 2*levelRarity)%modulo;
				nbstats -= (levelEffect-1)*effect.getCostStats();
				effect.setLevel(levelEffect);
			}
		}
		
		//attack and hp
		int bonusAttack = u%(1+nbstats/2);
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = beastAppearances[u%(beastAppearances.length)];
		
		return new BeastCard(appearance, typeCost, level, hp, attack, effects, levelRarity, true);
	}
	
	public static RobotCard robotCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int u = u0;
		int nbstats = 0;
		int levelRarity = 0;
		int level = 0;
		int attackmin = 0;
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		int nbMox = 0;
		if (u%4 == 3) {
			//1mox
			nbMox++;
			u = (u * multiplicator + 2*levelRarity)%modulo;
			if (u%4 == 3) {
				//2mox
				nbMox++;
				u = (u * multiplicator + 2*levelRarity)%modulo;
				if (u%4 == 3) {
					//3mox
					nbMox++;
					u = (u * multiplicator + 2*levelRarity)%modulo;
				}
			}
		}
		int lvlmax = 6;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			lvlmax++;
		}
		level = u%(lvlmax)+1;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (2+level)*globalStrengh/4 + levelRarity*rarityStrengh;
		
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%5>0) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName)) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		//effect2
		if (!stopEffects) {
		if (u%5>1) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect3
		if (!stopEffects) {
		if (u%5>2) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0) {
					attackmin++;
					nbstats -= 2;
				}
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect4
		if (!stopEffects) {
			if (u%5>3) {
				String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
				if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
					stopEffects = true;
				} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
					stopEffects = true;
				} else {
					sortedeffects.add(effectName);
					if (Effect.namesLevelEffects.contains(effectName)) {
						effects.add(new Effect(effectName, "robot", 1));
					} else {
						effects.add(new Effect(effectName, "robot"));
					}
					nbstats -= effects.get(effects.size()-1).getCostStats();
					if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0) {
						attackmin++;
						nbstats -= 2;
					}
				}
			} else {
				stopEffects = true;
			}
			u = (u * multiplicator + 2*levelRarity)%modulo;
			}
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
				u = (u * multiplicator + 2*levelRarity)%modulo;
				levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
				u = (u * multiplicator + 2*levelRarity)%modulo;
				nbstats -= (levelEffect-1)*effect.getCostStats();
				effect.setLevel(levelEffect);
			}
		}
		
		//attack and hp
		int bonusAttack = u%(1+nbstats/2);
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = robotAppearances[u%(robotAppearances.length)];
		
		return new RobotCard(appearance, level, hp, attack, effects, levelRarity, true);
	}
	
	public static BeastCard beastCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int u = u0;
		int nbstats = 0;
		int levelRarity = 0;
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		String typeCost = "blood";
		nbstats = globalStrengh/8 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%4>0) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		//effect2
		if (!stopEffects) {
		if (u%4>1) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect3
		if (!stopEffects) {
		if (u%4>2) {
			String effectName = Effect.namesBeastEffects.get(u%(Effect.namesBeastEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats  || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "beast", 1));
				} else {
					effects.add(new Effect(effectName, "beast"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
				u = (u * multiplicator + 2*levelRarity)%modulo;
				levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
				u = (u * multiplicator + 2*levelRarity)%modulo;
				nbstats -= (levelEffect-1)*effect.getCostStats();
				effect.setLevel(levelEffect);
			}
		}
		
		//attack and hp
		int hp = 1 + nbstats;
		return new BeastCard("squirrel", typeCost, 0, hp, 0, effects, levelRarity, false);
	}
	
	public static RobotCard robotCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int u = u0;
		int nbstats = 0;
		int levelRarity = 0;
		int level = 0;
		int attackmin = 0;
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		int nbMox = 0;
		if (u%4 == 3) {
			//1mox
			nbMox++;
			u = (u * multiplicator + 2*levelRarity)%modulo;
			if (u%4 == 3) {
				//2mox
				nbMox++;
				u = (u * multiplicator + 2*levelRarity)%modulo;
				if (u%4 == 3) {
					//3mox
					nbMox++;
					u = (u * multiplicator + 2*levelRarity)%modulo;
				}
			}
		}
		level = 1;
		nbstats = (2+level)*globalStrengh/4 + levelRarity*rarityStrengh;
		
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%5>0) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		//effect2
		if (!stopEffects) {
		if (u%5>1) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect3
		if (!stopEffects) {
		if (u%5>2) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "robot", 1));
				} else {
					effects.add(new Effect(effectName, "robot"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect4
		if (!stopEffects) {
			if (u%5>3) {
				String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
				if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
					stopEffects = true;
				} else {
					sortedeffects.add(effectName);
					if (Effect.namesLevelEffects.contains(effectName)) {
						effects.add(new Effect(effectName, "robot", 1));
					} else {
						effects.add(new Effect(effectName, "robot"));
					}
					nbstats -= effects.get(effects.size()-1).getCostStats();
					
				}
			} else {
				stopEffects = true;
			}
			u = (u * multiplicator + 2*levelRarity)%modulo;
			}
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
				u = (u * multiplicator + 2*levelRarity)%modulo;
				levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
				u = (u * multiplicator + 2*levelRarity)%modulo;
				nbstats -= (levelEffect-1)*effect.getCostStats();
				effect.setLevel(levelEffect);
			}
		}
		
		//hp
		int hp = 1 + nbstats;
		
		return new RobotCard("empty_vessel", level, hp, 0, effects, levelRarity, false);
	}
}