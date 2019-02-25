package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mod.RitualistMod;
import patches.MainEnum;

public class StrategicCut extends AbstractRitual {

    /*
    * COMMON Atk
    * 1E
    * Deal 7 damage next turn draw 2 cards
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("StrategicCut");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/strategic.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int MAGIC = 2;
    private static final int UPG_DMG = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /Stat Declaration/

    public StrategicCut() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(1));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
    }
    @Override
    public AbstractCard makeCopy() { return new StrategicCut(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}
