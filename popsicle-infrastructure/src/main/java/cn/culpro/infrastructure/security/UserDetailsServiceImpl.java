package cn.culpro.infrastructure.security;

import cn.culpro.domain.system.adapter.repository.IMenuRepository;
import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringSecurity用户详情服务实现
 * <p>
 * 实现UserDetailsService接口，加载用户信息和权限
 *
 * @author HogskinKitty
 * @date 2024/10/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final IUserRepository userRepository;
    
    private final IMenuRepository menuRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        Optional<UserAggregate> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("用户不存在：" + username);
        }
        
        UserAggregate user = userOpt.get();
        
        // 获取用户权限 - 直接从菜单仓储获取
        List<MenuAggregate> menus = menuRepository.findByUserId(user.getUserId());
        Set<String> permissions = menus.stream()
                .filter(menu -> menu.getPermission() != null && !menu.getPermission().isEmpty())
                .map(MenuAggregate::getPermission)
                .collect(Collectors.toSet());
        
        // 转换为SpringSecurity的GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // 添加权限作为Authority
        if (permissions != null && !permissions.isEmpty()) {
            authorities.addAll(
                    permissions.stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList()));
        }
        
        // 创建UserDetails对象返回
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(user.getDeleteStatus() == 1) // 删除状态为1表示已删除，禁用账户
                .build();
    }
} 