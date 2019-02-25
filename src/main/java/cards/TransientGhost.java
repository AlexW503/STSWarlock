package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DelayedPossessionRemovePower;
import powers.PossessionPower;

public class TransientGhost extends CustomCard {

    /*
    * COMMON Skill
    * 1E
    * Apply 5 Possession. In 3 turns that enemy loses 5 Possession.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("TransientGhost");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/transient.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 1;
    private static final int POS_AMT = 5;
    private static final int UPGRADE_MAGIC= 2;
    private static final int TURNS = 2;

    // /Stat Declaration/

    public TransientGhost() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = POS_AMT;
        magicNumber = baseMagicNumber;

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        AbstractPower power = p.getPower(DelayedPossessionRemovePower.POWER_ID);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PossessionPower(m, p, magicNumber), magicNumber));
        if (!m.hasPower("Artifact")) {
            if (power != null) {
                ((DelayedPossessionRemovePower)power).stackPower(magicNumber, TURNS);
                power.updateDescription();
            }
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DelayedPossessionRemovePower(m, magicNumber, TURNS), magicNumber));


        }
    }
    @Override
    public AbstractCard makeCopy() { return new TransientGhost(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }

}
