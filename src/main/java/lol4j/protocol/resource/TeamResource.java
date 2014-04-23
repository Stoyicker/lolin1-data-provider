package lol4j.protocol.resource;

import lol4j.protocol.dto.team.TeamDto;
import lol4j.util.Region;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron Corley on 12/13/13.
 */
public interface TeamResource {
    List<TeamDto> getTeams(long summonerId, Region region);

    TeamDto getTeam(String teamId, Region region);

    Map<String, TeamDto> getTeams(List<String> teamIds, Region region);
}
