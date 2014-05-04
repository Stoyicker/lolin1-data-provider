package lol4j.client.impl;

import lol4j.client.Lol4JClient;
import lol4j.protocol.dto.champion.ChampionListDto;
import lol4j.protocol.dto.game.RecentGamesDto;
import lol4j.protocol.dto.league.LeagueDto;
import lol4j.protocol.dto.league.LeagueItemDto;
import lol4j.protocol.dto.lolstaticdata.*;
import lol4j.protocol.dto.stats.PlayerStatsSummaryListDto;
import lol4j.protocol.dto.stats.RankedStatsDto;
import lol4j.protocol.dto.summoner.MasteryPagesDto;
import lol4j.protocol.dto.summoner.RunePagesDto;
import lol4j.protocol.dto.summoner.SummonerDto;
import lol4j.protocol.dto.team.TeamDto;
import lol4j.protocol.resource.*;
import lol4j.protocol.resource.impl.ResourceFactory;
import lol4j.service.impl.ApiRequestManager;
import lol4j.util.Region;
import lol4j.util.game.SubType;
import lol4j.util.lolstaticdata.*;
import lol4j.util.stats.Season;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron Corley on 12/10/13.
 */
public class Lol4JClientImpl implements Lol4JClient {
    private static final String LOL_BASE_ENDPOINT = "https://prod.api.pvp.net/api/lol";
    private static final String PATH_TO_API_KEY_FILE = "apiKey";
    private ChampionResource championResource;
    private GameResource gameResource;
    private LeagueResource leagueResource;
    private StatsResource statsResource;
    private SummonerResource summonerResource;
    private TeamResource teamResource;
    private LolStaticDataResource lolStaticDataResource;
    private ApiRequestManager apiRequestManager;
    private static Lol4JClient instance;

    public static Lol4JClient getInstance() {
        if (instance == null)
            instance = new Lol4JClientImpl();
        return instance;
    }

    private Lol4JClientImpl() {
        String apiKey = null;
        try {
            apiKey = new String(Files.readAllBytes(Paths.get(PATH_TO_API_KEY_FILE)), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        apiRequestManager = new ApiRequestManager(apiKey, LOL_BASE_ENDPOINT);
        ResourceFactory resourceFactory = new ResourceFactory(apiRequestManager);

        championResource = resourceFactory.createChampionResource();
        gameResource = resourceFactory.createGameResource();
        leagueResource = resourceFactory.createLeagueResource();
        statsResource = resourceFactory.createStatsResource();
        summonerResource = resourceFactory.createSummonerResource();
        teamResource = resourceFactory.createTeamResource();
        lolStaticDataResource = resourceFactory.createLolStaticDataResource();
    }

    @Override
    public ChampionListDto getAllChampions(Region region, boolean freeToPlay) {
        return championResource.getAllChampions(region, freeToPlay);
    }

    @Override
    public lol4j.protocol.dto.champion.ChampionDto getChampion(Region region, int id) {
        return championResource.getChampion(region, id);
    }

    @Override
    public RecentGamesDto getRecentGames(Region region, long summonerId) {
        return gameResource.getRecentGames(region, summonerId);
    }

    @Override
    public LeagueDto getChallengerLeague(Region region, SubType gameType) {
        return leagueResource.getChallengerLeague(region, gameType);
    }

    @Override
    public List<LeagueDto> getLeaguesData(long summonerId, Region region) {
        return leagueResource.getLeaguesData(summonerId, region);
    }

    @Override
    public List<LeagueItemDto> getLeaguesEntryData(long summonerId, Region region) {
        return leagueResource.getLeaguesEntryData(summonerId, region);
    }

    @Override
    public PlayerStatsSummaryListDto getPlayerStatsSummaries(long summonerId, Region region, Season season) {
        return statsResource.getPlayerStatsSummaries(summonerId, region, season);
    }

    @Override
    public RankedStatsDto getRankedStats(long summonerId, Region region, Season season) {
        return statsResource.getRankedStats(summonerId, region, season);
    }

    @Override
    public Map<String, MasteryPagesDto> getMasteryPages(List<Long> summonerIds, Region region) {
        return summonerResource.getMasteryPages(summonerIds, region);
    }

    @Override
    public MasteryPagesDto getMasteryPages(long summonerId, Region region) {
        return summonerResource.getMasteryPages(summonerId, region);
    }

    @Override
    public Map<String, RunePagesDto> getRunePages(List<Long> summonerIds, Region region) {
        return summonerResource.getRunePages(summonerIds, region);
    }

    @Override
    public RunePagesDto getRunePages(long summonerId, Region region) {
        return summonerResource.getRunePages(summonerId, region);
    }

    @Override
    public Map<String, SummonerDto> getSummonersByName(List<String> names, Region region) {
        return summonerResource.getSummonersByName(names, region);
    }

    @Override
    public SummonerDto getSummonerByName(String name, Region region) {
        return summonerResource.getSummonerByName(name, region);
    }

    @Override
    public Map<String, SummonerDto> getSummoners(List<Long> summonerIds, Region region) {
        return summonerResource.getSummoners(summonerIds, region);
    }

    @Override
    public SummonerDto getSummoner(long summonerId, Region region) {
        return summonerResource.getSummoner(summonerId, region);
    }

    @Override
    public Map<String, String> getSummonerNames(List<Long> summonerIds, Region region) {
        return summonerResource.getSummonerNames(summonerIds, region);
    }

    @Override
    public String getSummonerName(long summonerId, Region region) {
        return summonerResource.getSummonerName(summonerId, region);
    }

    @Override
    public List<TeamDto> getTeams(long summonerId, Region region) {
        return teamResource.getTeams(summonerId, region);
    }

    @Override
    public TeamDto getTeam(String teamId, Region region) {
        return teamResource.getTeam(teamId, region);
    }

    @Override
    public Map<String, TeamDto> getTeams(List<String> teamIds, Region region) {
        return teamResource.getTeams(teamIds, region);
    }

    @Override
    public lol4j.protocol.dto.lolstaticdata.ChampionListDto getChampionList(Region region, String locale, String version, List<ChampData> requestedData) {
        return lolStaticDataResource.getChampionList(region, locale, version, requestedData);
    }

    @Override
    public ChampionDto getChampion(String id, Region region, String locale, String version, List<ChampData> requestedData) {
        return lolStaticDataResource.getChampion(id, region, locale, version, requestedData);
    }

    @Override
    public BasicDataDto getItem(String id, Region region, String locale, String version, List<ItemData> requestedData) {
        return lolStaticDataResource.getItem(id, region, locale, version, requestedData);
    }

    @Override
    public ItemListDto getItemList(Region region, String locale, String version, List<ItemData> requestedData) {
        return lolStaticDataResource.getItemList(region, locale, version, requestedData);
    }

    @Override
    public MasteryDto getMastery(String id, Region region, String locale, String version, List<MasteryData> requestedData) {
        return lolStaticDataResource.getMastery(id, region, locale, version, requestedData);
    }

    @Override
    public MasteryListDto getMasteryList(Region region, String locale, String version, List<MasteryData> requestedData) {
        return lolStaticDataResource.getMasteryList(region, locale, version, requestedData);
    }

    @Override
    public RealmDto getRealm(Region region) {
        return lolStaticDataResource.getRealm(region);
    }

    @Override
    public RuneListDto getRuneList(Region region, String locale, String version, List<RuneData> requestedData) {
        return lolStaticDataResource.getRuneList(region, locale, version, requestedData);
    }

    @Override
    public BasicDataDto getRune(String id, Region region, String locale, String version, List<RuneData> requestedData) {
        return lolStaticDataResource.getRune(id, region, locale, version, requestedData);
    }

    @Override
    public SummonerSpellListDto getSummonerSpellList(Region region, String locale, String version, List<SummonerSpellData> requestedData) {
        return lolStaticDataResource.getSummonerSpellList(region, locale, version, requestedData);
    }

    @Override
    public SummonerSpellDto getSummonerSpell(String id, Region region, String locale, String version, List<SummonerSpellData> requestedData) {
        return lolStaticDataResource.getSummonerSpell(id, region, locale, version, requestedData);
    }

    @Override
    public void setRateLimit(int perTenSeconds, int perTenMinutes) {
        apiRequestManager.setRateLimit(perTenSeconds, perTenMinutes);
    }
}
