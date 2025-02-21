import java.util.*;

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

class CardCollection {
    private Map<String, Set<Card>> cardsBySuit;

    public CardCollection() {
        cardsBySuit = new HashMap<>();
    }

    // Add a card to the collection
    public String addCard(String rank, String suit) {
        cardsBySuit.putIfAbsent(suit, new HashSet<>());

        Card newCard = new Card(rank, suit);
        if (cardsBySuit.get(suit).contains(newCard)) {
            return "Error: Card \"" + newCard + "\" already exists.";
        }
        
        cardsBySuit.get(suit).add(newCard);
        return "Card added: " + newCard;
    }

    // Find all cards of a given suit
    public String findCardsBySuit(String suit) {
        if (!cardsBySuit.containsKey(suit) || cardsBySuit.get(suit).isEmpty()) {
            return "No cards found for " + suit + ".";
        }
        StringBuilder result = new StringBuilder();
        for (Card card : cardsBySuit.get(suit)) {
            result.append(card).append("\n");
        }
        return result.toString().trim();
    }

    // Display all stored cards
    public String displayAllCards() {
        if (cardsBySuit.isEmpty()) {
            return "No cards found.";
        }
        StringBuilder result = new StringBuilder();
        for (Set<Card> cardSet : cardsBySuit.values()) {
            for (Card card : cardSet) {
                result.append(card).append("\n");
            }
        }
        return result.toString().trim();
    }

    // Remove a card from the collection
    public String removeCard(String rank, String suit) {
        if (!cardsBySuit.containsKey(suit) || !cardsBySuit.get(suit).remove(new Card(rank, suit))) {
            return "Card \"" + rank + " of " + suit + "\" not found.";
        }

        // Remove the suit entry if it's empty after removal
        if (cardsBySuit.get(suit).isEmpty()) {
            cardsBySuit.remove(suit);
        }
        return "Card removed: " + rank + " of " + suit;
    }
}

// Main class with Switch Case Menu
public class CardCollectionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollection collection = new CardCollection();
        int choice;

        do {
            System.out.println("\n===== Card Collection System =====");
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Suit");
            System.out.println("3. Display All Cards");
            System.out.println("4. Remove Card");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add Card
                    System.out.print("Enter card rank (e.g., Ace, King, 10): ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter card suit (e.g., Spades, Hearts): ");
                    String suit = scanner.nextLine();
                    System.out.println(collection.addCard(rank, suit));
                    break;

                case 2: // Find Cards by Suit
                    System.out.print("Enter suit to find (e.g., Hearts): ");
                    String findSuit = scanner.nextLine();
                    System.out.println(collection.findCardsBySuit(findSuit));
                    break;

                case 3: // Display All Cards
                    System.out.println(collection.displayAllCards());
                    break;

                case 4: // Remove Card
                    System.out.print("Enter rank of card to remove: ");
                    String removeRank = scanner.nextLine();
                    System.out.print("Enter suit of card to remove: ");
                    String removeSuit = scanner.nextLine();
                    System.out.println(collection.removeCard(removeRank, removeSuit));
                    break;

                case 5: // Exit
                    System.out.println("Exiting the Card Collection System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }
