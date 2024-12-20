package fundsite.fund_web_backend.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fundsite.fund_web_backend.model.Donation;
import fundsite.fund_web_backend.model.DonationHistory;
import fundsite.fund_web_backend.model.User;
import fundsite.fund_web_backend.repository.DonationHistoryRepository;
import fundsite.fund_web_backend.repository.DonationRepository;
import fundsite.fund_web_backend.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class DonationService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public List<Donation> getTop3Donations() {
        return donationRepository.findAll().stream().limit(3).toList();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("기부 정보를 찾을 수 없습니다. ID: " + id));
    }
    
    
    @Transactional
    public void donate(Long donationId, Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new RuntimeException("Donation not found"));

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update user balance
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        // Update donation collected amount
        donation.setCollectedAmount(donation.getCollectedAmount() + amount);
        donationRepository.save(donation);

        // Create and save donation history
        DonationHistory history = new DonationHistory();
        history.setUser(user);
        history.setDonation(donation);
        history.setAmount(amount);
        history.setTimestamp(LocalDateTime.now());
        donationHistoryRepository.save(history);
    }
}
