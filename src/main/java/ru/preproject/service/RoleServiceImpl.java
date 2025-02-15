package ru.preproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.preproject.model.Role;
import ru.preproject.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Optional<Role> findRoleById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public void checkRoles() {
        Role role = new Role();

        if (findByRoleName("ROLE_ADMIN").isEmpty()) {
            role.setRoleName("ROLE_ADMIN");
            save(role);
        }

        if (findByRoleName("ROLE_USER").isEmpty()) {
            role.setRoleName("ROLE_USER");
            save(role);
        }
    }
}