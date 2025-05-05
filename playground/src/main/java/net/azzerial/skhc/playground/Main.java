package net.azzerial.skhc.playground;

import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.SKClientBuilder;
import net.azzerial.skhc.entities.Offer;
import net.azzerial.skhc.enums.Language;
import net.azzerial.skhc.enums.Region;
import net.azzerial.skhc.events.ListenerAdapter;
import net.azzerial.skhc.events.exchange.ExchangeEvent;
import net.azzerial.skhc.events.exchange.ExchangeUpdateEvent;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public final class Main {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.playground");

    /* Constructors */

    private Main() {
    }

    /* Methods */

    public static void main(String[] args) {
        final String username = System.getenv("SPIRAL_KNIGHTS_USERNAME");
        final String password = System.getenv("SPIRAL_KNIGHTS_PASSWORD");

        try {
            final SKClient client = SKClientBuilder.create(username, password)
                .setRegion(Region.EU_WEST)
                .setLanguage(Language.ENGLISH)
                .enableServices(Service.EXCHANGE)
                .build();

            client.addEventListeners(new ListenerAdapter() {
                @Override
                public void onExchange(@NotNull ExchangeEvent event) {
                    log.info("market: {}", event.getMarket());
                }

                @Override
                public void onExchangeUpdate(@NotNull ExchangeUpdateEvent event) {
                    event.handle(new ExchangeUpdateEvent.ExchangeUpdateHandler() {
                        @Override
                        public void onLastPriceChange(int newLastPrice, int oldLastPrice) {
                            log.info("lastPrice: {} -> {}", oldLastPrice, newLastPrice);
                        }

                        @Override
                        public void onBuyOffersChange(@NotNull Offer[] newBuyOffers, @NotNull Offer[] oldBuyOffers) {
                            log.info("buyOffers: {} -> {}", oldBuyOffers, newBuyOffers);
                        }

                        @Override
                        public void onSellOffersChange(@NotNull Offer[] newSellOffers, @NotNull Offer[] oldSellOffers) {
                            log.info("sellOffers: {} -> {}", oldSellOffers, newSellOffers);
                        }
                    });
                }
            });

            if (!client.connect()) {
                throw new IllegalStateException("This client is already connected!");
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}