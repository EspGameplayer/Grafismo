package grafismo.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, grafismo.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, grafismo.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, grafismo.domain.User.class.getName());
            createCache(cm, grafismo.domain.Authority.class.getName());
            createCache(cm, grafismo.domain.User.class.getName() + ".authorities");
            createCache(cm, grafismo.domain.PersistentToken.class.getName());
            createCache(cm, grafismo.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, grafismo.domain.Broadcast.class.getName());
            createCache(cm, grafismo.domain.Broadcast.class.getName() + ".broadcastPersonnelMembers");
            createCache(cm, grafismo.domain.Team.class.getName());
            createCache(cm, grafismo.domain.Team.class.getName() + ".venues");
            createCache(cm, grafismo.domain.Team.class.getName() + ".children");
            createCache(cm, grafismo.domain.Team.class.getName() + ".competitions");
            createCache(cm, grafismo.domain.Person.class.getName());
            createCache(cm, grafismo.domain.Player.class.getName());
            createCache(cm, grafismo.domain.Player.class.getName() + ".positions");
            createCache(cm, grafismo.domain.StaffMember.class.getName());
            createCache(cm, grafismo.domain.Referee.class.getName());
            createCache(cm, grafismo.domain.Referee.class.getName() + ".matches");
            createCache(cm, grafismo.domain.Referee.class.getName() + ".competitions");
            createCache(cm, grafismo.domain.BroadcastPersonnelMember.class.getName());
            createCache(cm, grafismo.domain.BroadcastPersonnelMember.class.getName() + ".broadcasts");
            createCache(cm, grafismo.domain.Association.class.getName());
            createCache(cm, grafismo.domain.Venue.class.getName());
            createCache(cm, grafismo.domain.Venue.class.getName() + ".teams");
            createCache(cm, grafismo.domain.Location.class.getName());
            createCache(cm, grafismo.domain.Shirt.class.getName());
            createCache(cm, grafismo.domain.Match.class.getName());
            createCache(cm, grafismo.domain.Match.class.getName() + ".referees");
            createCache(cm, grafismo.domain.Match.class.getName() + ".deductions");
            createCache(cm, grafismo.domain.Match.class.getName() + ".suspensions");
            createCache(cm, grafismo.domain.Match.class.getName() + ".injuries");
            createCache(cm, grafismo.domain.MatchPlayer.class.getName());
            createCache(cm, grafismo.domain.MatchPlayer.class.getName() + ".lineups");
            createCache(cm, grafismo.domain.MatchPlayer.class.getName() + ".actions");
            createCache(cm, grafismo.domain.Lineup.class.getName());
            createCache(cm, grafismo.domain.Lineup.class.getName() + ".matchPlayers");
            createCache(cm, grafismo.domain.Formation.class.getName());
            createCache(cm, grafismo.domain.TemplateFormation.class.getName());
            createCache(cm, grafismo.domain.TeamPlayer.class.getName());
            createCache(cm, grafismo.domain.TeamStaffMember.class.getName());
            createCache(cm, grafismo.domain.MatchAction.class.getName());
            createCache(cm, grafismo.domain.MatchAction.class.getName() + ".matchPlayers");
            createCache(cm, grafismo.domain.Sponsor.class.getName());
            createCache(cm, grafismo.domain.Competition.class.getName());
            createCache(cm, grafismo.domain.Competition.class.getName() + ".teams");
            createCache(cm, grafismo.domain.Competition.class.getName() + ".referees");
            createCache(cm, grafismo.domain.Competition.class.getName() + ".children");
            createCache(cm, grafismo.domain.Matchday.class.getName());
            createCache(cm, grafismo.domain.Deduction.class.getName());
            createCache(cm, grafismo.domain.Suspension.class.getName());
            createCache(cm, grafismo.domain.Injury.class.getName());
            createCache(cm, grafismo.domain.Season.class.getName());
            createCache(cm, grafismo.domain.SystemConfiguration.class.getName());
            createCache(cm, grafismo.domain.ActionKey.class.getName());
            createCache(cm, grafismo.domain.Position.class.getName());
            createCache(cm, grafismo.domain.Position.class.getName() + ".parents");
            createCache(cm, grafismo.domain.Position.class.getName() + ".children");
            createCache(cm, grafismo.domain.Position.class.getName() + ".players");
            createCache(cm, grafismo.domain.Country.class.getName());
            createCache(cm, grafismo.domain.GraphicElement.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
