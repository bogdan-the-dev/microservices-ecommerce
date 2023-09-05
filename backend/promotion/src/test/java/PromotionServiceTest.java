import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import ro.bogdansoftware.promotion.IPromotionRepository;
import ro.bogdansoftware.promotion.Promotion;
import ro.bogdansoftware.promotion.PromotionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private IPromotionRepository repository;

    @Test
    public void testDisablePromotionInvalidId() {
        String invalidPromotionId = "invalidId";
        List<String> ids = new ArrayList<>();
        ids.add(invalidPromotionId);

        when(repository.findById(invalidPromotionId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            promotionService.disablePromotion(ids);
        });
    }
    @Test
    public void testEditPromotion() {
        String promotionId = "123";
        Promotion existingPromotion = new Promotion();
        existingPromotion.setId(promotionId);
        existingPromotion.setName("Old Promotion");
        existingPromotion.setPercentage(BigDecimal.valueOf(10));

        Promotion updatedPromotion = new Promotion();
        updatedPromotion.setId(promotionId);
        updatedPromotion.setName("New Promotion");
        updatedPromotion.setPercentage(BigDecimal.valueOf(20));

        when(repository.findById(promotionId)).thenReturn(Optional.of(existingPromotion));

        promotionService.editPromotion(updatedPromotion);

        verify(repository, times(1)).save(existingPromotion);
        assertEquals(updatedPromotion.getName(), existingPromotion.getName());
        assertEquals(updatedPromotion.getPercentage(), existingPromotion.getPercentage());
    }

    @Test
    public void testEditPromotionInvalidId() {
        String invalidPromotionId = "invalidId";
        Promotion promotion = new Promotion();
        promotion.setId(invalidPromotionId);

        when(repository.findById(invalidPromotionId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            promotionService.editPromotion(promotion);
        });

        assertEquals("Invalid promotion id", exception.getMessage());
    }

    @Test
    public void testDeletePromotionInvalidId() {
        String invalidPromotionId = "invalidId";
        List<String> ids = new ArrayList<>();
        ids.add(invalidPromotionId);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            promotionService.deletePromotion(ids);
        });

        assertEquals("Invalid promotion id", exception.getMessage());
    }

}
