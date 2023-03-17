package cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import effects.Effect;

public class CardFactory {
	public static String beastAppearances[] = {"kingfisher", "raven_egg", "sparrow", "magpie", "raven", "turkey_vulture", "stunted_wolf","wolf_cub","bloodhound","dire_wolf","wolf"};
	
	public static String robotAppearances[] = {"xformerbatbot","s0n1a","xformerporcupinebot","qu177","xformergrizzlybot","gr1zz","bomb_latcher","exeskeleton","shield_latcher","skel_e_latcher"};
	
	public static String undeadAppearances[] = {"armored_zombie","bone_lord","bone_lords_horn","bone_pile","bone_prince","dead_hand","dead_pets","draugr"};
	
	public static Card mainCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0, String type) throws IOException {
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		if (type.equals("beast")) {
			return beastCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		} else if (type.equals("robot")) {
			return robotCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		}
		
		return undeadCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
	}
	
	public static Card sourceCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0, String type) throws IOException {
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		if (type.equals("beast")) {
			return beastCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		} else if (type.equals("robot")) {
			return robotCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		}
		
		return undeadCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
	}
	
	
	private static BeastCard beastCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
		int level = 0;
		int attackmin = 0;
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
				if (Effect.namesResourceEffects.contains(effect.getName())) {
					int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
					ArrayList<Integer> integerSeen = new ArrayList<Integer>();
					while (u%4 == 3 && !integerSeen.contains(u)) {
						integerSeen.add(u);
						u = (u * multiplicator + 2*levelRarity)%modulo;
						lvlmaxEffect++;
					}
					int levelEffect = 1 + u%lvlmaxEffect;
					u = (u * multiplicator + 2*levelRarity)%modulo;
					nbstats -= (levelEffect-1)*effect.getCostStats();
					effect.setLevel(levelEffect);
				} else {
					int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
					u = (u * multiplicator + 2*levelRarity)%modulo;
					levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
					u = (u * multiplicator + 2*levelRarity)%modulo;
					nbstats -= (levelEffect-1)*effect.getCostStats();
					effect.setLevel(levelEffect);
				}
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

	private static UndeadCard undeadCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
		int level = 0;
		int attackmin = 0;
		int lvlmax = 10;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			lvlmax++;
		}
		level = 1 + u%(lvlmax-1);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		level = Math.min(lvlmax, 1 + u%(lvlmax-1));
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (level+1) * globalStrengh / 4 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%4>0) {
			String effectName = Effect.namesUndeadEffects.get(u%(Effect.namesUndeadEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "undead", 1));
				} else {
					effects.add(new Effect(effectName, "undead"));
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
			String effectName = Effect.namesUndeadEffects.get(u%(Effect.namesUndeadEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "undead", 1));
				} else {
					effects.add(new Effect(effectName, "undead"));
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
			String effectName = Effect.namesUndeadEffects.get(u%(Effect.namesUndeadEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "undead", 1));
				} else {
					effects.add(new Effect(effectName, "undead"));
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
				if (Effect.namesResourceEffects.contains(effect.getName())) {
					int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
					ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
					while (u%4 == 3 && !integerSeen2.contains(u)) {
						integerSeen2.add(u);
						u = (u * multiplicator + 2*levelRarity)%modulo;
						lvlmaxEffect++;
					}
					int levelEffect = 1 + u%lvlmaxEffect;
					u = (u * multiplicator + 2*levelRarity)%modulo;
					nbstats -= (levelEffect-1)*effect.getCostStats();
					effect.setLevel(levelEffect);
				} else {
					int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
					u = (u * multiplicator + 2*levelRarity)%modulo;
					levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
					u = (u * multiplicator + 2*levelRarity)%modulo;
					nbstats -= (levelEffect-1)*effect.getCostStats();
					effect.setLevel(levelEffect);
				}
			}
		}
		
		//attack and hp
		int bonusAttack = u%(1+nbstats/2);
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = undeadAppearances[u%(undeadAppearances.length)];
		
		return new UndeadCard(appearance, level, hp, attack, effects, levelRarity, true);
	}
	
	
	private static RobotCard robotCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
		int level = 0;
		int attackmin = 0;
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
						if (Effect.namesResourceEffects.contains(effect.getName())) {
							int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen2.contains(u)) {
								integerSeen2.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							int levelEffect = 1 + u%lvlmaxEffect;
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						} else {
							int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
							u = (u * multiplicator + 2*levelRarity)%modulo;
							levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						}
					}
				}
		
		//attack and hp
		int bonusAttack = u%(1+nbstats/2);
		if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
			bonusAttack = u%(1+nbstats/3);
		}
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = robotAppearances[u%(robotAppearances.length)];
		
		return new RobotCard(appearance, level, hp, attack, effects, levelRarity, true);
	}
	
	private static BeastCard beastCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
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
						if (Effect.namesResourceEffects.contains(effect.getName())) {
							int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u)) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							int levelEffect = 1 + u%lvlmaxEffect;
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						} else {
							int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
							u = (u * multiplicator + 2*levelRarity)%modulo;
							levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						}
					}
				}
		
		//attack and hp
		int hp = 1 + nbstats;
		return new BeastCard("squirrel", typeCost, 0, hp, 0, effects, levelRarity, false);
	}
	
	private static RobotCard robotCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
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
						if (Effect.namesResourceEffects.contains(effect.getName())) {
							int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u)) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							int levelEffect = 1 + u%lvlmaxEffect;
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						} else {
							int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
							u = (u * multiplicator + 2*levelRarity)%modulo;
							levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						}
					}
				}
		
		//hp
		int hp = 1 + nbstats;
		
		return new RobotCard("empty_vessel", level, hp, 0, effects, levelRarity, false);
	}

	private static UndeadCard undeadCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
		nbstats = globalStrengh/8 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		sortedeffects.add("brittle");
		effects.add(new Effect("brittle", "undead"));
		
		//effect2
		if (!stopEffects) {
		if (u%4>1) {
			String effectName = Effect.namesUndeadEffects.get(u%(Effect.namesUndeadEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "undead", 1));
				} else {
					effects.add(new Effect(effectName, "undead"));
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
			String effectName = Effect.namesUndeadEffects.get(u%(Effect.namesUndeadEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "undead", 1));
				} else {
					effects.add(new Effect(effectName, "undead"));
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
						if (Effect.namesResourceEffects.contains(effect.getName())) {
							int lvlmaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u)) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							int levelEffect = 1 + u%lvlmaxEffect;
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						} else {
							int levelEffect = 1 + u%(1+nbstats/effect.getCostStats());
							u = (u * multiplicator + 2*levelRarity)%modulo;
							levelEffect = Math.min(levelEffect, 1 + u%(1+nbstats/effect.getCostStats()));
							u = (u * multiplicator + 2*levelRarity)%modulo;
							nbstats -= (levelEffect-1)*effect.getCostStats();
							effect.setLevel(levelEffect);
						}
					}
				}
		
		//attack and hp
		int bonusAttack = u%(1+nbstats/2);
		int attack = 1 + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;

		return new UndeadCard("skeleton", 0, hp, attack, effects, levelRarity, false);
	}
}
