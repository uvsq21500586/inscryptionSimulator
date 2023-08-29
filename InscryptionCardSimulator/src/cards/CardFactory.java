package cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import effects.Effect;

public class CardFactory {
	public static String beastAppearances[] = {"kingfisher", "raven_egg", "sparrow", "magpie", "raven", "turkey_vulture", "stunted_wolf","wolf_cub","bloodhound","dire_wolf",
			"wolf","coyote"};
	
	public static String beastFeatheredAppearances[] = {"kingfisher", "raven_egg", "sparrow", "magpie", "raven", "turkey_vulture"};
	public static String beastCanineAppearances[] = {"stunted_wolf","wolf_cub","bloodhound","dire_wolf",
			"wolf","coyote"};
	
	public static String robotAppearances[] = {"xformerbatbot","s0n1a","xformerporcupinebot","qu177","xformergrizzlybot","gr1zz","bomb_latcher","exeskeleton","shield_latcher","skel_e_latcher",
			"buff_conduit","gems_conduit"};
	
	public static String undeadAppearances[] = {"armored_zombie","bone_lord","bone_lords_horn","bone_pile","bone_prince","dead_hand","pharaoh_pets","draugr","drowned_soul","ember_spirit"};
	
	public static String wizardAppearances[] = {"alchemist","amalgam_wiz","bignificus","blue_mage","blue_mage_fused","boss_stim_mage","fire_node","flying_mage"};
	
	public static Card mainCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0, String type) throws IOException {
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		if (type.equals("beast")) {
			return beastCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,0);
		} else if (type.equals("robot")) {
			return robotCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,0);
		} else if (type.equals("undead")) {
			return undeadCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,0);
		}
		
		return wizardCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,0);
	}
	
	public static Card mainCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0, String type, int difficulty, boolean rarityGarantee) throws IOException {
		int u = u0;
		
		int levelRarity = 0;
		if (rarityGarantee) {
			levelRarity = 1;
		}
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		if (type.equals("beast")) {
			return beastCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,difficulty);
		} else if (type.equals("robot")) {
			return robotCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,difficulty);
		} else if (type.equals("undead")) {
			return undeadCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,difficulty);
		}
		
		return wizardCard(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity,difficulty);
	}
	
	public static Card sourceCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0, String type) throws IOException {
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		if (type.equals("beast")) {
			return beastCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		} else if (type.equals("robot")) {
			return robotCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		} else if (type.equals("undead")) {
			return undeadCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
		}
		
		return wizardCardSource(modulo, multiplicator, globalStrengh, rarityStrengh, u, levelRarity);
	}
	
	
	private static BeastCard beastCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity, int difficulty) throws IOException {
		int nbstats = 0;
		int level = 0;
		int attackmin = 0;
		String typeCost = "blood";
		if (u%4 == 3) {
			typeCost = "bone";
			u = (u * multiplicator + 2*levelRarity)%modulo;
			int lvlmax = 8;
			ArrayList<Integer> integerSeen = new ArrayList<Integer>();
			while (u%5 == 4 && !integerSeen.contains(u)) {
				integerSeen.add(u);
				u = (u * multiplicator + 2*levelRarity)%modulo;
				lvlmax++;
			}
			u = (u * multiplicator + 2*levelRarity)%modulo;
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
			u = (u * multiplicator + 2*levelRarity)%modulo;
			level = 1 + u%(lvlmax);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = level * globalStrengh + levelRarity * rarityStrengh;
			nbstats = nbstats - u%(1+globalStrengh/4);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = nbstats + Math.max(0, level - 1 - u%(1+globalStrengh/4));
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		
		nbstats = Math.max(0, nbstats + difficulty);
		
		return beastCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin, typeCost);
	}
	
	public static BeastCard beastCardFixedLevel(String typeCost, int level,int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int nbstats = 0;
		int attackmin = 0;
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		if (typeCost.equals("blood")) {
			nbstats = level * globalStrengh + levelRarity * rarityStrengh;
			nbstats = nbstats - u%(1+globalStrengh/4);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			nbstats = nbstats + Math.max(0, level - 1 - u%(1+globalStrengh/4));
			u = (u * multiplicator + 2*levelRarity)%modulo;
		} else {
			nbstats = level * globalStrengh / 4 + levelRarity * rarityStrengh;
			nbstats = nbstats - u%(1+globalStrengh/8);
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}	
		
		return beastCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin, typeCost);
	}

	private static BeastCard beastCardFactory(int modulo, int multiplicator, int u, int levelRarity, int nbstats,
			int level, int attackmin, String typeCost) throws IOException {
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		String tribe = "";
		if (u%2==0) {
			tribe = "feathered";
		}else if (u%2==1) {
			tribe = "canine";
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		if (tribe.equals("feathered") || tribe.equals("canine")) {
			if (attackmin == 0) {
				nbstats = Math.max(nbstats-2, 0);
			}
			attackmin = Math.max(attackmin, 1);
		}
		
		//effect1
		if (tribe.equals("feathered")) {
			effects.add(new Effect("airborne", "beast"));
		} else if (u%4>0) {
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
		//too strong combinaison
				Optional<Effect> unkillable = effects.stream().filter(effect->effect.getName().equals("unkillable")).findFirst();
				if (unkillable.isPresent() && effects.stream().anyMatch(effect -> Effect.namesEffectsTooStrongWithUnkillable.contains(effect.getName()))) {
					effects.remove(unkillable.get());
					nbstats += unkillable.get().getCostStats();
				}
				
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				if (Effect.namesResourceEffects.contains(effect.getName())) {
					int lvlmaxEffect = 1;
					int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
					ArrayList<Integer> integerSeen = new ArrayList<Integer>();
					while (u%4 == 3 && !integerSeen.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
						integerSeen.add(u);
						u = (u * multiplicator + 2*levelRarity)%modulo;
						lvlmaxEffect++;
					}
					u = (u * multiplicator + 2*levelRarity)%modulo;
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
		if (tribe.equals("feathered"))  {
			appearance = beastFeatheredAppearances[u%(beastFeatheredAppearances.length)];
		}
		if (tribe.equals("canine"))  {
			appearance = beastCanineAppearances[u%(beastCanineAppearances.length)];
		}
		
		return new BeastCard(appearance, typeCost, level, hp, attack, effects, levelRarity, true);
	}

	
	
	private static UndeadCard undeadCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity, int difficulty) throws IOException {
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
		u = (u * multiplicator + 2*levelRarity)%modulo;
		level = 1 + u%(lvlmax);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		level = Math.min(level, 1 + u%(lvlmax));
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (level+1) * globalStrengh / 4 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = Math.max(0, nbstats + difficulty);
		return undeadCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin);
	}
	
	private static UndeadCard undeadCardFactory(int modulo, int multiplicator, int u, int levelRarity, int nbstats, int level, int attackmin) throws IOException {
		
		
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
		
		//too strong combinaison
		Optional<Effect> unkillable = effects.stream().filter(effect->effect.getName().equals("unkillable")).findFirst();
		if (unkillable.isPresent() && effects.stream().anyMatch(effect -> Effect.namesEffectsTooStrongWithUnkillable.contains(effect.getName()))) {
			effects.remove(unkillable.get());
			nbstats += unkillable.get().getCostStats();
		}
		
		//levels effects
		for (int i=0;i<effects.size();i++) {
			Effect effect = effects.get(i);
			if (Effect.namesLevelEffects.contains(effect.getName())) {
				if (Effect.namesResourceEffects.contains(effect.getName())) {
					int lvlmaxEffect = 1;
					int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
					ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
					while (u%4 == 3 && !integerSeen2.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
						integerSeen2.add(u);
						u = (u * multiplicator + 2*levelRarity)%modulo;
						lvlmaxEffect++;
					}
					u = (u * multiplicator + 2*levelRarity)%modulo;
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
	
	public static UndeadCard undeadCardFixedLevel(int level,int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int nbstats = 0;
		int attackmin = 0;
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (1+level) * globalStrengh / 4 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;	
		
		return undeadCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin);
	}
	
	private static RobotCard robotCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity, int difficulty) throws IOException {
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
		u = (u * multiplicator + 2*levelRarity)%modulo;
		level = u%(lvlmax)+1;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (2+level)*globalStrengh/4 + levelRarity*rarityStrengh;
		if (level > 6) {
			nbstats = (2+level+2*Math.max(0, level-6))*globalStrengh/4 + levelRarity*rarityStrengh;
		}
		nbstats = Math.max(0, nbstats + difficulty);
		return robotCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin);
	}

	private static RobotCard robotCardFactory(int modulo, int multiplicator, int u, int levelRarity, int nbstats, int level, int attackmin) throws IOException {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
				} else if (effectName.equals("unkillable") && level < 3) {
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen2.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen2.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
							u = (u * multiplicator + 2*levelRarity)%modulo;
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
				int costAttack = 2;
				if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
					costAttack ++;
				}
				if (effects.stream().anyMatch(effect -> effect.getName().equals("trifurcated_strike"))) {
					costAttack += 2;
				}
				int bonusAttack = u%(1+nbstats/costAttack);
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = robotAppearances[u%(robotAppearances.length)];
		
		return new RobotCard(appearance, level, hp, attack, effects, levelRarity, true);
	}
	
	public static RobotCard robotCardFixedLevel(int level,int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int nbstats = 0;
		int attackmin = 0;
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (2+level) * globalStrengh / 4 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;	
		
		return robotCardFactory(modulo, multiplicator, u, levelRarity, nbstats, level, attackmin);
	}
	
	private static WizardCard wizardCard(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity, int difficulty) throws IOException {
		int nbstats = 0;
		int level = 0;
		int attackmin = 0;
		int nbAnyMox = 0;
		int nbGreenMox = 0;
		int nbOrangeMox = 0;
		int nbBlueMox = 0;
		List<String> listWizardEffects = new ArrayList<>();
		/*if (u%4 == 3) {
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
		}*/
		int lvlmax = 1;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplicator + 2*levelRarity)%modulo;
			lvlmax++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		level = u%(lvlmax)+1;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		
		if (level == 1) {
			if (u%3 == 0) {
				nbGreenMox ++;
				listWizardEffects = Effect.namesWizardGreenEffects;
			}
			if (u%3 == 1) {
				nbOrangeMox ++;
				listWizardEffects = Effect.namesWizardOrangeEffects;
			}
			if (u%3 == 2) {
				nbBlueMox ++;
				listWizardEffects = Effect.namesWizardBlueEffects;
			}
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		if (level == 2) {
			if (u%2 == 0) {
				//one color
				u = (u * multiplicator + 2*levelRarity)%modulo;
				if (u%3 == 0) {
					nbGreenMox ++;
					u = (u * multiplicator + 2*levelRarity)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbGreenMox ++;
					}
					listWizardEffects = Effect.namesWizardGreenEffects;
				} else if (u%3 == 1) {
					nbOrangeMox ++;
					u = (u * multiplicator + 2*levelRarity)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbOrangeMox ++;
					}
					listWizardEffects = Effect.namesWizardOrangeEffects;
				} else {
					nbBlueMox ++;
					u = (u * multiplicator + 2*levelRarity)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbBlueMox ++;
					}
					listWizardEffects = Effect.namesWizardBlueEffects;
				}
			} else {
				//two colors
				u = (u * multiplicator + 2*levelRarity)%modulo;
				if (u%3 == 0) {
					nbOrangeMox ++;
					nbBlueMox ++;
					listWizardEffects = Effect.namesWizardOrangeBlueEffects;
					
				}
				if (u%3 == 1) {
					nbGreenMox ++;
					nbBlueMox ++;
					listWizardEffects = Effect.namesWizardGreenBlueEffects;
				}
				if (u%3 == 2) {
					nbGreenMox ++;
					nbOrangeMox ++;
					listWizardEffects = Effect.namesWizardGreenOrangeEffects;
				}
			}
		}
		if (level>2) {
			if (u%3 == 0) {
				//one color
				u = (u * multiplicator + 2*levelRarity)%modulo;
				int colorGem = u%3;
				u = (u * multiplicator + 2*levelRarity)%modulo;
				int nbcoloredGem = 1+u%level;
				nbAnyMox += level - nbcoloredGem;
				if (colorGem == 0) {
					nbGreenMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardGreenEffects;
				}
				if (colorGem == 1) {
					nbOrangeMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardOrangeEffects;
				}
				if (colorGem == 2) {
					nbBlueMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardBlueEffects;
				}
				u = (u * multiplicator + 2*levelRarity)%modulo;
			} else if (u%3 == 1) {
				//two colors
				u = (u * multiplicator + 2*levelRarity)%modulo;
				int absentColorGem = u%3;
				u = (u * multiplicator + 2*levelRarity)%modulo;
				int nbcoloredGem = 1+u%(level/2);
				nbAnyMox += level - 2 *nbcoloredGem;
				if (absentColorGem == 0) {
					nbOrangeMox += nbcoloredGem;
					nbBlueMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardOrangeBlueEffects;
				}
				if (absentColorGem == 1) {
					nbGreenMox += nbcoloredGem;
					nbBlueMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardGreenBlueEffects;
				}
				if (absentColorGem == 2) {
					nbGreenMox += nbcoloredGem;
					nbOrangeMox += nbcoloredGem;
					listWizardEffects = Effect.namesWizardGreenOrangeEffects;
				}
				u = (u * multiplicator + 2*levelRarity)%modulo;
			} else {
				//all colors
				u = (u * multiplicator + 2*levelRarity)%modulo;
				int nbcoloredGem = 1+u%(level/3);
				nbAnyMox += level - 3 *nbcoloredGem;
				nbGreenMox += nbcoloredGem;
				nbOrangeMox += nbcoloredGem;
				nbBlueMox += nbcoloredGem;
				listWizardEffects = Effect.namesWizardGreenOrangeBlueEffects;
				u = (u * multiplicator + 2*levelRarity)%modulo;
			}
		}
		
		nbstats = (2*nbAnyMox + 3*(level-nbAnyMox))*globalStrengh/4 + levelRarity*rarityStrengh;
		nbstats = Math.max(0, nbstats + difficulty);
		
		/*//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%4>0) {
			String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "wizard", 1));
				} else {
					effects.add(new Effect(effectName, "wizard"));
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
			String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "wizard", 1));
				} else {
					effects.add(new Effect(effectName, "wizard"));
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
			String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "wizard", 1));
				} else {
					effects.add(new Effect(effectName, "wizard"));
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen2.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen2.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
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
		int costAttack = 2;
		if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
			costAttack ++;
		}
		if (effects.stream().anyMatch(effect -> effect.getName().equals("trifurcated_strike"))) {
			costAttack += 2;
		}
		int bonusAttack = u%(1+nbstats/costAttack);
		int attack = attackmin + bonusAttack;
		int hp = 1 + nbstats - 2*bonusAttack;
		u = (u * multiplicator + 2*levelRarity)%modulo;
		String appearance = wizardAppearances[u%(wizardAppearances.length)];
		
		return new WizardCard(appearance, nbAnyMox, nbGreenMox, nbOrangeMox, nbBlueMox, level, hp, attack, effects, levelRarity, true);*/
		
		return wizardCardFactory(modulo, multiplicator, u, levelRarity, nbstats, listWizardEffects, level,
				 nbAnyMox, nbGreenMox, nbOrangeMox, nbBlueMox, attackmin);
		
	}
	
	public static WizardCard wizardCardFixedLevel(int level, int nbAnyMox, int nbGreenMox, int nbOrangeMox, int nbBlueMox,int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u0) throws IOException {
		int nbstats = 0;
		int attackmin = 0;
		int u = u0;
		
		int levelRarity = 0;
		
		while (u%10 == 9) {
			u = (u * multiplicator)%modulo;
			levelRarity++;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		nbstats = (2+level) * globalStrengh / 4 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/8);
		u = (u * multiplicator + 2*levelRarity)%modulo;	
		List<String> listWizardEffects = new ArrayList<>();
		
		if (nbGreenMox>0 && nbOrangeMox==0 && nbBlueMox==0) {
			listWizardEffects = Effect.namesWizardGreenEffects;
			
		}
		if (nbGreenMox==0 && nbOrangeMox>0 && nbBlueMox==0) {
			listWizardEffects = Effect.namesWizardOrangeEffects;
		}
		if (nbGreenMox==0 && nbOrangeMox==0 && nbBlueMox>0) {
			listWizardEffects = Effect.namesWizardBlueEffects;
		}
		if (nbGreenMox>0 && nbOrangeMox>0 && nbBlueMox==0) {
			listWizardEffects = Effect.namesWizardGreenOrangeEffects;
		}
		if (nbGreenMox>0 && nbOrangeMox==0 && nbBlueMox>0) {
			listWizardEffects = Effect.namesWizardGreenBlueEffects;
		}
		if (nbGreenMox==0 && nbOrangeMox>0 && nbBlueMox>0) {
			listWizardEffects = Effect.namesWizardOrangeBlueEffects;
		}
		if (nbGreenMox==0 && nbOrangeMox==0 && nbBlueMox==0) {
			listWizardEffects = Effect.namesWizardGreenOrangeBlueEffects;
		}
		
		return wizardCardFactory(modulo, multiplicator, u, levelRarity, nbstats, listWizardEffects, level,
				 nbAnyMox, nbGreenMox, nbOrangeMox, nbBlueMox, attackmin);
	}
	
	private static WizardCard wizardCardFactory(int modulo, int multiplicator, int u, int levelRarity, int nbstats, List<String> listWizardEffects, int level,
			 int nbAnyMox, int nbGreenMox, int nbOrangeMox, int nbBlueMox, int attackmin) throws IOException {
		//effects
				boolean stopEffects = false;
				List<Effect> effects = new ArrayList<>();
				List<String> sortedeffects = new ArrayList<>();
				
				//effect1
				if (u%4>0) {
					String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
					if (Effect.mapEffectToCost.get(effectName) > nbstats) {
						stopEffects = true;
					} else if (Effect.namesAttackEffects.contains(effectName) && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
						stopEffects = true;
					} else {
						sortedeffects.add(effectName);
						if (Effect.namesLevelEffects.contains(effectName)) {
							effects.add(new Effect(effectName, "wizard", 1));
						} else {
							effects.add(new Effect(effectName, "wizard"));
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
					String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
					if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
						stopEffects = true;
					} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
						stopEffects = true;
					} else {
						sortedeffects.add(effectName);
						if (Effect.namesLevelEffects.contains(effectName)) {
							effects.add(new Effect(effectName, "wizard", 1));
						} else {
							effects.add(new Effect(effectName, "wizard"));
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
					String effectName = listWizardEffects.get(u%(listWizardEffects.size()));
					if (Effect.mapEffectToCost.get(effectName) > nbstats || sortedeffects.contains(effectName)) {
						stopEffects = true;
					} else if (Effect.namesAttackEffects.contains(effectName) && attackmin == 0 && Effect.mapEffectToCost.get(effectName) + 2 > nbstats) {
						stopEffects = true;
					} else {
						sortedeffects.add(effectName);
						if (Effect.namesLevelEffects.contains(effectName)) {
							effects.add(new Effect(effectName, "wizard", 1));
						} else {
							effects.add(new Effect(effectName, "wizard"));
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
									int lvlmaxEffect = 1;
									int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
									ArrayList<Integer> integerSeen2 = new ArrayList<Integer>();
									while (u%4 == 3 && !integerSeen2.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
										integerSeen2.add(u);
										u = (u * multiplicator + 2*levelRarity)%modulo;
										lvlmaxEffect++;
									}
									u = (u * multiplicator + 2*levelRarity)%modulo;
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
				int costAttack = 2;
				if (effects.stream().anyMatch(effect -> effect.getName().equals("bifurcated_strike"))) {
					costAttack ++;
				}
				if (effects.stream().anyMatch(effect -> effect.getName().equals("trifurcated_strike"))) {
					costAttack += 2;
				}
				int bonusAttack = u%(1+nbstats/costAttack);
				int attack = attackmin + bonusAttack;
				int hp = 1 + nbstats - 2*bonusAttack;
				u = (u * multiplicator + 2*levelRarity)%modulo;
				String appearance = wizardAppearances[u%(wizardAppearances.length)];
				
				return new WizardCard(appearance, nbAnyMox, nbGreenMox, nbOrangeMox, nbBlueMox, level, hp, attack, effects, levelRarity, true);
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
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
		nbstats = (1+level)*globalStrengh/4 + levelRarity*rarityStrengh;
		if (level == 1) {
			nbstats = nbstats - u%(1+globalStrengh/4);
		} else {
			nbstats = nbstats - u%(1+globalStrengh/8);
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		//effect1
		if (u%5>0) {
			String effectName = Effect.namesRobotEffects.get(u%(Effect.namesRobotEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
			} else if (effectName.equals("unkillable") && level < 3) {
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
				} else if (effectName.equals("unkillable") && level < 3) {
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
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
	
	private static WizardCard wizardCardSource(int modulo, int multiplicator, int globalStrengh, int rarityStrengh, int u, int levelRarity) throws IOException {
		int nbstats = 0;
		nbstats = globalStrengh/2 + levelRarity * rarityStrengh;
		nbstats = nbstats - u%(1+globalStrengh/4);
		nbstats = Math.max(2, nbstats);
		u = (u * multiplicator + 2*levelRarity)%modulo;
		//effects
		boolean stopEffects = false;
		List<Effect> effects = new ArrayList<>();
		List<String> sortedeffects = new ArrayList<>();
		
		int maxGemEffects = 1;
		if (nbstats >= 4) {
			maxGemEffects = 2;
		}
		if (nbstats >= 6) {
			maxGemEffects = 3;
		}
		int nbGemEffects = 1;
		if (nbGemEffects < maxGemEffects && u%4 == 3) {
			nbGemEffects ++;
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		if (nbGemEffects < maxGemEffects && u%4 == 3) {
			nbGemEffects ++;
			u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		
		if (nbGemEffects == 1) {
			if (u%3 == 0) {
				effects.add(new Effect("green_gem", "wizard", 1));
				sortedeffects.add("green_gem");
			}
			if (u%3 == 1) {
				effects.add(new Effect("orange_gem", "wizard", 1));
				sortedeffects.add("orange_gem");
			}
			if (u%3 == 2) {
				effects.add(new Effect("blue_gem", "wizard", 1));
				sortedeffects.add("blue_gem");
			}
			nbstats -= 2;
		}
		if (nbGemEffects == 2) {
			if (u%3 == 0) {
				effects.add(new Effect("orange_gem", "wizard", 1));
				effects.add(new Effect("blue_gem", "wizard", 1));
				sortedeffects.add("orange_gem");
				sortedeffects.add("blue_gem");
			}
			if (u%3 == 1) {
				effects.add(new Effect("green_gem", "wizard", 1));
				effects.add(new Effect("blue_gem", "wizard", 1));
				sortedeffects.add("green_gem");
				sortedeffects.add("blue_gem");
			}
			if (u%3 == 2) {
				effects.add(new Effect("green_gem", "wizard", 1));
				effects.add(new Effect("orange_gem", "wizard", 1));
				sortedeffects.add("green_gem");
				sortedeffects.add("orange_gem");
			}
			nbstats -= 4;
		}
		if (nbGemEffects == 3) {
			effects.add(new Effect("green_gem", "wizard", 1));
			effects.add(new Effect("orange_gem", "wizard", 1));
			effects.add(new Effect("blue_gem", "wizard", 1));
			sortedeffects.add("green_gem");
			sortedeffects.add("orange_gem");
			sortedeffects.add("blue_gem");
			nbstats -= 6;
		}
		
		//effect1
		//gem effect
		
		//effect2
		if (!stopEffects && effects.size()<2) {
		if (u%4>1) {
			String effectName = Effect.namesWizardMoxEffects.get(u%(Effect.namesWizardMoxEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "wizard", 1));
				} else {
					effects.add(new Effect(effectName, "wizard"));
				}
				nbstats -= effects.get(effects.size()-1).getCostStats();
				
			}
		} else {
			stopEffects = true;
		}
		u = (u * multiplicator + 2*levelRarity)%modulo;
		}
		//effect3
		if (!stopEffects && effects.size()<3) {
		if (u%4>2) {
			String effectName = Effect.namesWizardMoxEffects.get(u%(Effect.namesWizardMoxEffects.size()));
			if (Effect.mapEffectToCost.get(effectName) > nbstats || Effect.namesAttackEffects.contains(effectName) || sortedeffects.contains(effectName)) {
				stopEffects = true;
			} else {
				sortedeffects.add(effectName);
				if (Effect.namesLevelEffects.contains(effectName)) {
					effects.add(new Effect(effectName, "wizard", 1));
				} else {
					effects.add(new Effect(effectName, "wizard"));
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
							int lvlmaxEffect = 1;
							int lvlmaxMaxEffect = 1 + nbstats/effect.getCostStats();
							ArrayList<Integer> integerSeen = new ArrayList<Integer>();
							while (u%4 == 3 && !integerSeen.contains(u) && lvlmaxEffect < lvlmaxMaxEffect) {
								integerSeen.add(u);
								u = (u * multiplicator + 2*levelRarity)%modulo;
								lvlmaxEffect++;
							}
							u = (u * multiplicator + 2*levelRarity)%modulo;
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

		return WizardCard.sourceCard(hp, effects, levelRarity);
	}
	
	private boolean incompatibleEffect(String effect, String beastType) {
		if (beastType.equals("canine")) {
			List<String> incompatibleEffects = Arrays.asList("airborne");
			return incompatibleEffects.contains(effect);
		}
		return false;
	}
}
