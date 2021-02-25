package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import mod.RitualistMod;
import patches.MainEnum;

import java.util.ArrayList;
import java.util.Iterator;

public class ViolentFlux extends CustomCard {

    /*
    * RARE Attack
    * 2E
    * Deal !D! Damage to the frontal enemy, then deal the previous damage + !M! to the next enemy, repeating.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("ViolentFlux");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/violent_flux.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 3;
    private static final int DAMAGE = 20;
    private static final int DAMAGE_UPG = 5;
    private static final int DMG_INC = 5;
    private static final int UPG_DMG_INC = 5;

    // /Stat Declaration/

    public ViolentFlux() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = DMG_INC;
        magicNumber = baseMagicNumber;

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.getMonsters().queueMonsters();
        int bonus = 0;

        while(!AbstractDungeon.actionManager.monsterQueue.isEmpty()) {
            m = AbstractDungeon.actionManager.monsterQueue.get(0).monster;
            if (!m.isDeadOrEscaped() || m.halfDead)
                addToBot(new DamageAction(m, new DamageInfo(p, damage+bonus, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                bonus+=magicNumber;
            AbstractDungeon.actionManager.monsterQueue.remove(0);
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.5F));

        }

    }

    @Override
    public AbstractCard makeCopy() { return new ViolentFlux(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UPG);
            upgradeMagicNumber(UPG_DMG_INC);
      //      rawDescription = UPGRADE_DESCRIPTION;
         //   initializeDescription();
        }
    }

}
