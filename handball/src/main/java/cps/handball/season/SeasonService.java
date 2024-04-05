package cps.handball.season;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public Season createSeason(Timestamp seasonStartDate,
                               Timestamp seasonEndDate){
        Season season = new Season(seasonStartDate, seasonEndDate);
        return seasonRepository.save(season);
    }
}
