package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;
import powers.SoulRotPower;

import java.util.Iterator;

public class RotInPieces extends CustomCard {
    /*
    * Rare SKILL
    * 2E
    *Turn all enemies' Possession into Soul Rot. NL Soul Rot applies that much Possession at the start of each of their turn.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("RotInPieces");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/aoeS.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;
    public static final String P_ID = PossessionPower.POWER_ID;
    private static final int COST = 2;
    private static final int UPGRADE = 1;
 //   private static final int POS = 1;
    private int amount;
    // /Stat Declaration/


    public RotInPieces() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        //baseMagicNumber = POS;
        //magicNumber = baseMagicNumber;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        AbstractMonster mo;

        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            if(mo.hasPower(P_ID)) {
                amount = mo.getPower(P_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, mo, P_ID));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new SoulRotPower(mo, p, amount), amount));

            }
        }


    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RotInPieces();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE);
            //upgradeMagicNumber(UPGRADE_MAG);
            initializeDescription();
        }
    }

}
