package actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import patches.MainEnum;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrivalAction extends AbstractGameAction {

    private AbstractPlayer p;
  //  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    public static final String TEXT = "Select a Summon to add to your hand";
            //[] TEXT = uiStrings.TEXT;
    private ArrayList<AbstractCard> nonSums = new ArrayList();

    public ArrivalAction() {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {

        Consumer<List<AbstractCard>> callback =  list ->
        { addToBot(new LoseHPAction(p, p, list.size())); list.forEach(c -> c.upgrade());

        };

        new SelectCardsAction(p.discardPile.group, 1,"this", true, c->true, callback);
        Iterator<AbstractCard> c;
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.discardPile.size() == 1) {

                card = this.p.discardPile.getTopCard();

                if (card.hasTag(MainEnum.SUMMON_CARD)) {
                    card.unfadeOut();
                    this.p.hand.addToHand(card);

                    this.p.discardPile.removeCard(card);

                    card.unhover();
                    card.fadingOut = false;

                }
                this.isDone = true;
                return;
            } else {
                c = this.p.discardPile.group.iterator();

                while (c.hasNext()) {
                    card = c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                c = this.p.discardPile.group.iterator();

                while(c.hasNext()) {
                    card = c.next();
                    if (!card.hasTag(MainEnum.SUMMON_CARD)) {
                        c.remove();
                        this.nonSums.add(card);
                    }
                }

                if (this.p.discardPile.isEmpty()) {
                    this.p.discardPile.group.addAll(this.nonSums);
                    this.nonSums.clear();
                    this.isDone = true;
                    return;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, TEXT, false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = c.next();
                    this.p.hand.addToHand(card);

                    this.p.discardPile.removeCard(card);
                    /*
                    if (this.upgrade && card.canUpgrade()) {
                        card.upgrade();
                    }*/
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();

                this.p.discardPile.group.addAll(this.nonSums);
                this.nonSums.clear();

                for (c = this.p.discardPile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard) c.next();
                    card.unhover();
                    card.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }

    }
}

