package ru.preproject.service;

import ru.preproject.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public void save(Role role);
    public List<Role> findAll();

    public Optional<Role> findByRoleName(String roleName);

    public Optional<Role> findRoleById(long id);
    public void delete(Role role);
    public void deleteRoleById(long id);
    public void checkRoles();
}
